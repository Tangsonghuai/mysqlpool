package utils

import java.util.Properties


object PropertyUtils {
  def getFileProperties(fileName: String, propertyKey: String): String = {
    val result = this.getClass.getClassLoader.getResourceAsStream(fileName)
    val prop = new Properties
    prop.load(result)
    prop.getProperty(propertyKey)

  }

//  def main(args: Array[String]): Unit = {
//    println( getFileProperties("mysql-user.properties","mysql.pool.jdbc.maxStatements"))
//  }

}
