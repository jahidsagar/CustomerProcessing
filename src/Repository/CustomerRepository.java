package Repository;

import java.util.List;

public interface CustomerRepository {
	boolean batchInsert(List<?> customerList, String tableName);
}
