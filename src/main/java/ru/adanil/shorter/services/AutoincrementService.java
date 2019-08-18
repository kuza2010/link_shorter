package ru.adanil.shorter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.adanil.shorter.Base62;
import ru.adanil.shorter.ShrinkError;
import ru.adanil.shorter.repository.DBData;
import ru.adanil.shorter.repository.model.LinkAutoincrement;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
//TODO refactor + exception + delete mongo operations
public class AutoincrementService {

    public static final String LINK_DB_VALUE = DBData.LinkSequence.Value.VALUE.name;

    public static final String DB_ID_NAME = DBData.LinkSequence.Columns.ID.name;
    public static final String DB_SEQUENCES_NAME = DBData.LinkSequence.Columns.SEQ.name;

    private Logger log = LoggerFactory.getLogger(AutoincrementService.class);

    @Autowired
    private MongoOperations mongo;

    /**
     * This method is a counter.
     * This function return unique id converted to {@link Base62}
     *
     * @return id converted to {@link Base62}
     *
     * @throws {@link ShrinkError} if unique id could not generate
     */
    public String getUniqPostfix() throws ShrinkError {
        LinkAutoincrement counter = mongo.findAndModify(
                query(where(DB_ID_NAME).is(LINK_DB_VALUE)),
                new Update().inc(DB_SEQUENCES_NAME, 1),
                options().returnNew(true).upsert(true),
                LinkAutoincrement.class);

        if (counter == null) {
            log.warn("getUniqPostfix: autoincrement counter is null");
            insertStartId();
            int id = getNextLinkId(0);
            return convertIDToBase62(id);
        }

        log.info("getUniqPostfix: next id = {}", counter.getSeq());
        return convertIDToBase62(counter.getSeq());
    }

    private int getNextLinkId(int retryCount) throws ShrinkError {
        if (retryCount >= 3) {
            log.error("getNextLinkId: Can not generate unique id. retry count = {}", retryCount);
            throw new ShrinkError("Can not generate unique id");
        }

        LinkAutoincrement counter = mongo.findAndModify(
                query(where(DB_ID_NAME).is(LINK_DB_VALUE)),
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
        log.warn("insertStartId: insert start id = 1");
    }

    private String convertIDToBase62(int id) {
        String postfix = Base62.getBase62From10(id);
        log.info("convertIDToBase62: create short {}, id = {}", postfix, id);

        return postfix;
    }

}
