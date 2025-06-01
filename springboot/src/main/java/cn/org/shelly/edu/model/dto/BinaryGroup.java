package cn.org.shelly.edu.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class BinaryGroup<N extends Serializable, V extends Serializable> implements Serializable {
    private N name;
    private V value;

    public BinaryGroup(N name, V value) {
        this.name = name;
        this.value = value;
    }

    public BinaryGroup() {
    }

    public List<BinaryGroup<N,V>> getList(Map<N,V> map) {
        List<BinaryGroup<N, V>> binaryGroups = new ArrayList<>();

        for(Map.Entry<N,V> entry : map.entrySet()) {
            BinaryGroup<N, V> binaryGroup = new BinaryGroup<>(entry.getKey(),entry.getValue());
            binaryGroups.add(binaryGroup);
        }

        return binaryGroups;
    }
}
