package e.iot.betbuddy.data;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class DataHolder {
    Map<String, WeakReference<Object>> data = new HashMap<String, WeakReference<Object>>();
    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}
    public void save(String id, Object object) {
        data.put(id, new WeakReference<Object>(object));
    }

    public Object retrieve(String id) {
        WeakReference<Object> objectWeakReference = data.get(id);
        return objectWeakReference.get();
    }
}