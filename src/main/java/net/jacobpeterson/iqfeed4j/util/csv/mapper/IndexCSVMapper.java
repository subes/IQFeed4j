package net.jacobpeterson.iqfeed4j.util.csv.mapper;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static net.jacobpeterson.iqfeed4j.util.csv.CSVUtil.valuePresent;

/**
 * {@inheritDoc}
 * <br>
 * {@link IndexCSVMapper} mappings are based off of predefined CSV indices.
 */
public class IndexCSVMapper<T> extends CSVMapper<T> {

    protected final HashMap<Integer, MappingFunctions<?>> mappingFunctionsOfCSVIndices;

    /**
     * Instantiates a new {@link IndexCSVMapper}.
     *
     * @param pojoInstantiator a {@link Callable} to instantiate a new POJO
     */
    public IndexCSVMapper(Callable<T> pojoInstantiator) {
        super(pojoInstantiator);

        mappingFunctionsOfCSVIndices = new HashMap<>();
    }

    /**
     * Adds a CSV index to POJO field mapping as the CSV index being the largest {@link #setMapping(int, BiConsumer,
     * Function)} CSV index + 1.
     *
     * @param <P>                    the type of the POJO field
     * @param fieldSetter            see {@link CSVMapper.MappingFunctions} constructor doc
     * @param stringToFieldConverter see {@link CSVMapper.MappingFunctions} constructor doc
     */
    public <P> void addMapping(BiConsumer<T, P> fieldSetter, Function<String, P> stringToFieldConverter) {
        int nextCSVIndex = mappingFunctionsOfCSVIndices.keySet().stream().max(Integer::compareTo).orElse(-1) + 1;
        setMapping(nextCSVIndex, fieldSetter, stringToFieldConverter);
    }

    /**
     * Sets a CSV index to POJO field mapping.
     *
     * @param <P>                    the type of the POJO field
     * @param csvIndex               the CSV index
     * @param fieldSetter            see {@link CSVMapper.MappingFunctions} constructor doc
     * @param stringToFieldConverter see {@link CSVMapper.MappingFunctions} constructor doc
     */
    public <P> void setMapping(int csvIndex, BiConsumer<T, P> fieldSetter, Function<String, P> stringToFieldConverter) {
        mappingFunctionsOfCSVIndices.put(csvIndex, new MappingFunctions<P>(fieldSetter, stringToFieldConverter));
    }

    /**
     * Removes a CSV mapping, if it exists.
     *
     * @param csvIndex the CSV index
     */
    public void removeMapping(int csvIndex) {
        mappingFunctionsOfCSVIndices.remove(csvIndex);
    }

    /**
     * {@inheritDoc}
     * <br>
     * Note this will map with the mappings added via {@link #setMapping(int, BiConsumer, Function)}.
     */
    @Override
    public T map(String[] csv, int offset) throws Exception {
        T instance = pojoInstantiator.call();

        // Loop through all added 'MappingFunctions' and apply them
        for (int csvIndex : mappingFunctionsOfCSVIndices.keySet()) {
            if (!valuePresent(csv, csvIndex + offset)) { // Don't map empty CSV values
                continue;
            }

            // apply() could throw a variety of exceptions
            try {
                mappingFunctionsOfCSVIndices.get(csvIndex).apply(instance, csv[csvIndex + offset]);
            } catch (Exception exception) {
                throw new Exception("Error mapping at index " + csvIndex + " with offset " + offset, exception);
            }
        }

        return instance;
    }
}
