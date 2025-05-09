package agenda.dal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 会议编号生成类
 * 
 * <p>
 * 该类负责会议编号的持久化存储和读取操作
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月15日
 * @lastUpdated 2025年3月28日
 * 
 */
public class AgendaIdGenerator {

    /** ID 文件存储路径 */
    private static final String ID_FILE = "C:" + File.separator + "java" + File.separator + "ids.dat";
    
    /** 单例模式实例 */
    private static final AgendaIdGenerator INSTANCE = new AgendaIdGenerator();
    
    /** 确保线程安全的计数器 */
    private final AtomicLong currentId;

    /**
     * 构造方法
     * 
     * <p>
     * 初始化当前ID为从文件加载的上一个ID
     * </p>
     * 
     */
    private AgendaIdGenerator() {
        // 加载文件中存储的上一个ID，或者初始化为0
        this.currentId = new AtomicLong(loadLastId());
    }

    /**
     * 获取单例实例
     *
     * @return 返回 AgendaIdGenerator 的单例实例
     */
    public static AgendaIdGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * 获取当前ID（即上次保存的ID）
     *
     * @return 当前ID
     */
    public long getId() {
        return loadLastId();
    }

    /**
     * 获取下一个ID（原子性增加当前ID并持久化存储）
     *
     * @return 下一个ID
     */
    public synchronized long nextId() {
        // 获取下一个ID
        long newId = this.currentId.incrementAndGet();
        
        // 将新ID保存到文件
        this.persistId(newId);
        
        // 返回新生成的ID
        return newId;
    }

    /**
     * 重置ID（将ID递减）
     */
    public synchronized void resetId() {
        // 将当前ID减一
        long newId = this.currentId.decrementAndGet();
        
        // 将新ID保存到文件
        this.persistId(newId);
    }

    /**
     * 从文件加载上一个ID
     *
     * @return 返回上一个ID，若文件不存在则返回0
     */
    private long loadLastId() {
        File file = new File(ID_FILE);
        
        // 如果文件不存在，则返回0
        if (!file.exists()) return 0L;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            // 从文件中读取并返回ID
            return dis.readLong();
        } catch (IOException e) {
            // 如果读取失败，返回0
            return 0L;
        }
    }

    /**
     * 将当前ID持久化保存到文件
     *
     * @param id 要保存的ID
     */
    private void persistId(long id) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ID_FILE))) {
            // 将ID写入文件
            dos.writeLong(id);
        } catch (IOException e) {
            // 如果保存失败，则抛出异常
            throw new RuntimeException("ID持久化失败", e);
        }
    }
    
}
