package cst.util.common.db;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author gwc
 * @version v201804 线程事务DAO 同一线程,使用同一事务
 */
public abstract class AbstractThreadTxDAO extends AbstractDAO {
	private ThreadLocal<Connection> conns = new ThreadLocal<Connection>() {
		protected Connection initialValue() {
			Connection conn = getConnection();
			try {
				conns.get().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
		};
	};
	
	public void rollback(){
		try {
			conns.get().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void commit() {
		try {
			conns.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void CloseConnection(Connection conn) {
		try {
			conns.get().close();
			conns.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void beginTx(){
		
	}
}
