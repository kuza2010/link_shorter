package ru.adanil.shorter.services;

public interface AutoincrementService {

    /**
     * This method increment and get next id.
     * Based on return value a unique short links will be generated.
     *
     * @return next id
     * @throws {@link ShrinkError} if unique id could not generate
     */
    int getAndIncrement() throws ShrinkError;
}
