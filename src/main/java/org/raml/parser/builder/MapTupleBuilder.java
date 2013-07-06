package org.raml.parser.builder;

import java.util.HashMap;

import org.raml.parser.resolver.DefaultScalarTupleHandler;
import org.raml.parser.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class MapTupleBuilder extends DefaultTupleBuilder<ScalarNode, MappingNode>
{

    private Class<?> keyClass;
    private Class valueClass;
    private String keyValue;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public MapTupleBuilder(String keyValue, Class<?> keyClass, Class<?> valueClass)
    {
        super(new DefaultScalarTupleHandler(MappingNode.class, keyValue));
        this.keyValue = keyValue;
        this.keyClass = keyClass;
        this.valueClass = valueClass;
    }

    @Override
    public TupleBuilder getBuiderForTuple(NodeTuple tuple)
    {
        return new PojoTupleBuilder(valueClass);
    }

    @Override
    public Object buildValue(Object parent, MappingNode node)
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        ReflectionUtils.setProperty(parent, keyValue, map);
        return map;
    }

    @Override
    public void buildKey(Object parent, ScalarNode tuple)
    {
        keyValue = tuple.getValue();
    }

}