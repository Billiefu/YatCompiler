package agenda.ui;

/**
 * 工厂类
 * 
 * <p>
 * 用于工厂设计模式，使用反射机制自动生成相应的命令类别，处理不同的命令。
 * 该类提供了一个静态方法，通过反射机制创建并返回相应的类的实例。
 * 该方法通过传入的类名和类型信息来实现不同命令的实例化。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年03月27日
 * @lastUpdated 2025年03月28日
 * 
 */
public class Factory {
    
    /** 私有化构造方法，避免实例化Factory类 */
    private Factory() {}

    /**
     * getInstance方法
     * 
     * <p>
     * 使用反射机制根据类名和类型信息创建并返回指定类型的实例。
     * 该方法接收类名和类型信息，通过反射生成相应的类的实例，并返回该实例。
     * 如果反射创建过程中发生异常，则返回null。
     * </p>
     * 
     * @param className 需要实例化的类的完全限定名
     * @param clazz 需要返回的实例的类类型
     * @param <T> 返回的实例的类型
     * @return 创建的类实例
     * 
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(String className, Class<T> clazz) {
        T instance = null; // 声明实例变量并初始化为null
        try {
            // 通过反射机制获取类的实例
            instance = (T) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch(Exception e) {
            // 如果发生异常，当前方法会返回null
        }
        return instance; // 返回创建的实例
    }
    
}
