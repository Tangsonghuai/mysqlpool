package utils

import java.sql.Connection

import com.mchange.v2.c3p0.ComboPooledDataSource


object MySQLPoolManager {
  var mysqlManager: MysqlPool = _

  def getMysqlManager: MysqlPool = {
    synchronized {
      if (mysqlManager == null) {
        mysqlManager = new MysqlPool
      }
    }
    mysqlManager
  }

  class MysqlPool extends Serializable {



    private val cpds: ComboPooledDataSource = new ComboPooledDataSource(true)



    try {

      

      cpds.setUser(PropertyUtils.getFileProperties("mysql-user.properties", "mysql.pool.jdbc.userName"))
      cpds.setPassword(PropertyUtils.getFileProperties("mysql-user.properties", "mysql.pool.jdbc.passWord"))
      cpds.setJdbcUrl(PropertyUtils.getFileProperties("mysql-user.properties", "mysql.pool.jdbc.url"))
      cpds.setDriverClass(PropertyUtils.getFileProperties("mysql-user.properties", "mysql.pool.jdbc.driverClass"))
      cpds.setMinPoolSize(PropertyUtils.getFileProperties("mysql-user.properties", "mysql.pool.jdbc.minPoolSize").toInt)
      cpds.setMaxPoolSize(PropertyUtils.getFileProperties("mysql-user.properties", "mysql.pool.jdbc.maxPoolSize").toInt)
      cpds.setAcquireIncrement(PropertyUtils.getFileProperties("mysql-user.properties", "mysql.pool.jdbc.acquireIncrement").toInt)
      cpds.setMaxStatements(PropertyUtils.getFileProperties("mysql-user.properties", "mysql.pool.jdbc.maxStatements").toInt)
    } catch {
      case e: Exception => e.printStackTrace()
      case e: NullPointerException => e.printStackTrace()
    }

    def getConnection: Connection = {
      try {
        cpds.getConnection()

      } catch {
        case ex: Exception =>
          ex.printStackTrace()
          null
      }
    }

    def close(): Unit = {
      try {
        cpds.close()
      } catch {
        case ex: Exception =>
          ex.printStackTrace()
      }
    }
  }

}
