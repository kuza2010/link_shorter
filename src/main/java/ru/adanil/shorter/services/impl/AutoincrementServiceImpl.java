package ru.adanil.shorter.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.adanil.shorter.repository.DBData;
import ru.adanil.shorter.repository.model.LinkAutoincrement;
import ru.adanil.shorter.services.AutoincrementService;
import ru.adanil.shorter.services.ShrinkError;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AutoincrementServiceImpl implements AutoincrementService {

    private static final String LINK_DB_VALUE = DBData.LinkSequence.Value.VALUE.name;
    private static final String DB_ID_NAME = DBData.LinkSequence.Columns.ID.name;
    private static final String DB_SEQUENCES_NAME = DBData.LinkSequence.Columns.SEQ.name;

    private Logger log = LoggerFactory.getLogger(AutoincrementServiceImpl.class);

    @Autowired
    private MongoOperations mongo;

    public int getAndIncrement() throws ShrinkError {
        LinkAutoincrement counter = mongo
                .findAndModify(
                        query(where(DB_ID_NAME).is(LINK_DB_VALUE)),
                        new Update().inc(DB_SEQUENCES_NAME, 1),
                        options().returnNew(true).upsert(true),
                        LinkAutoincrement.class);

        if (counter == null) {
            log.warn("getAndIncrement: autoincrement counter is null");
            insertStartId();
            return getAndIncrement(0);
        }

        log.info("getAndIncrement: next id = {}", counter.getSeq());
        return counter.getSeq();
    }

    private int getAndIncrement(int retryCount) throws ShrinkError {
        if (retryCount >= 3) {
            log.error("getAndIncrement: Can not increment id. retry count = {}", retryCount);
            throw new ShrinkError("Can not generate unique id");
        }

        LinkAutoincrement counter = mongo.findAndModify(
                query(where(DB_ID_NAME).is(LINK_DB_VALUE)),
                new Update().inc(DB_SEQUENCES_NAME, 1),
                options().returnNew(true).upsert(true),
                LinkAutoincrement.class);

        if (counter == null) {
            insertStartId();
            getAndIncrement(retryCount = +1);
        }

        return counter.getSeq();
    }

    private void insertStartId() {
        LinkAutoincrement toInsert = new LinkAutoincrement();
        toInsert.setId(DB_SEQUENCES_NAME);
        toInsert.setSeq(-1);

        mongo.insert(toInsert, LinkAutoincrement.COLLECTION_NAME);
        log.warn("insertStartId: insert start id = -1");
    }

}
