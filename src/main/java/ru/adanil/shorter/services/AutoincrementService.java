package ru.adanil.shorter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.adanil.shorter.repository.model.LinkAutoincrement;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AutoincrementService {

    public static final String LINK_DB_NAME = "link_id";

    public static final String DB_ID_NAME = "_id";
    public static final String DB_SEQUENCES_NAME = "seq";

    private Logger log = LoggerFactory.getLogger(AutoincrementService.class);

    @Autowired
    private MongoOperations mongo;

    public int getNextLinkId() {
        LinkAutoincrement counter = mongo.findAndModify(
                query(where(DB_ID_NAME).is(LINK_DB_NAME)),
                new Update().inc(DB_SEQUENCES_NAME, 1),
                options().returnNew(true).upsert(true),
                LinkAutoincrement.class);

        if (counter == null) {
            log.warn("getNextLinkId: autoincrement counter is null");
            insertStartId();
            return getNextLinkId(0);
        }

        log.info("getNextLinkId: next id = {}",counter.getSeq());
        return counter.getSeq();
    }

    private int getNextLinkId(int retryCount) {
        if (retryCount >= 3) {
            log.error("getNextLinkId: Can not generate unique id. retry count = {}",retryCount);
            throw new RuntimeException("Can not generate unique id");
        }

        LinkAutoincrement counter = mongo.findAndModify(
                query(where(DB_ID_NAME).is(LINK_DB_NAME)),
                new Update().inc(DB_SEQUENCES_NAME, 1),
                options().returnNew(true).upsert(true),
                LinkAutoincrement.class);

        if (counter == null) {
            insertStartId();
            getNextLinkId(retryCount++);
        }

        return counter.getSeq();
    }

    private void insertStartId() {
        LinkAutoincrement toInsert = new LinkAutoincrement();
        toInsert.setId(DB_SEQUENCES_NAME);
        toInsert.setSeq(-1);

        mongo.insert(toInsert, LinkAutoincrement.COLLECTION_NAME);
        log.info("insertStartId: insert start id = 1");
    }

}
