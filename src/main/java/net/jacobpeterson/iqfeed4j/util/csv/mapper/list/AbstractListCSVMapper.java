package net.jacobpeterson.iqfeed4j.util.csv.mapper.list;

import net.jacobpeterson.iqfeed4j.util.csv.mapper.AbstractCSVMapper;
import net.jacobpeterson.iqfeed4j.util.csv.mapper.CSVMapping;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * {@inheritDoc}
 * <br>
 * {@link AbstractListCSVMapper} maps a CSV list.
 */
public abstract class AbstractListCSVMapper<T> extends AbstractCSVMapper<T> {

    /**
     * Instantiates a new {@link AbstractListCSVMapper}.
     *
     * @param pojoInstantiator a {@link Callable} to instantiate a new POJO
     */
    public AbstractListCSVMapper(Callable<T> pojoInstantiator) {
        super(pojoInstantiator);
    }

    /**
     * Maps the given 'csv' list to a {@link List}.
     *
     * @param csv    the CSV
     * @param offset offset to add to CSV indices when applying {@link CSVMapping}s
     *
     * @return a new {@link List} of mapped POJOs
     *
     * @throws Exception thrown for a variety of {@link Exception}s
     */
    public abstract List<T> mapToList(String[] csv, int offset) throws Exception;
}