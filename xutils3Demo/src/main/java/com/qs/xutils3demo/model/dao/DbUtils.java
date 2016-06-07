package com.qs.xutils3demo.model.dao;

import android.util.Log;

import com.qs.xutils3demo.model.dao.table.Person;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by xuyang on 16/5/24.
 */
public class DbUtils {


    DbManager db ;

    public DbUtils() {
        DbManager.DaoConfig  daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName("xy.db");//设置数据库名字
        daoConfig.setDbVersion(1);//设置数据库的版本
        daoConfig.setDbDir(new File("/sdcard"));
        daoConfig.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                //关于数据库升级的注意事项
                /**
                 * 1: 数据库升级的话一般是增加某些字段,可以通过 alert 这个SQL 语句修改表,增加对应的字段,另外也可以通过 db.addColum 这个方法给对应的表增加字段
                 * 2:升级的时候应当判断版本号,因为有些用户可能是跨越多个版本更新的,比如从软件1直接升级到了2,中间的1.x没有更新过,但是这个数据库中表的字段增加是以1.x 中某个版本为准的
                 * 1这个版本无法直接修改升级到最新的2,那应该怎么办呢? 可以用 sql 语句 drop 掉这张表,然后创建新的表进去,但是在 drop 之前应该保存原始的数据,然后将对应的数据通过对应的方法重新加回到新的数据库中
                 * 中间可能会牵扯到字段变化等事项,需要自己注意下原始数据的某个字段应该添加到新的什么字段
                 * 另外可以通过 db.dropTable(Person.class);//删除某张表然后重新创建,但是也得注意保存数据
                 *
                 */
            }
        });

        db = x.getDb(daoConfig);
    }


    public void init(){

        Person person = new Person();
        person.setName("xy");
        person.setAge("23");
        person.setEmail("1053302578@qq.com");
        addOrUpdate(person);



        try {
            List<Person> children = db.selector(Person.class).findAll();
            for (Person us: children) {
                Log.e("TAG",us.getName()+"===="+us.getAge()+"===="+us.getEmail());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void add(Person person) {
        try {
            db.save(person);//添加对象到表中
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加或者是更新
     * @param person
     */
    public void addOrUpdate(Person person) {
        try {
            db.saveOrUpdate(person);//这个方法 如果不存在就添加,存在就更新,将原始的值更新为传递的对象上面的新值,判断存在的标准就是主键是否存在,这种方式需要在对应的类文件中手动指定一个字段为主键,不允许使用自增主键
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void delete(Person person) {
        try {
            db.delete(person);//从数据库中删除指定的对象对应行,这个必须也是手动指定唯一的主键,判断标准也是以主键为准
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void deleteByid(Object object) {
        try {
            db.deleteById(Person.class, object);//根据主键删除对应的对象,第一个参数指定是表名,因为类的注解中声明了表明,第二个参数为主键对应的值
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            db.delete(Person.class);//根据类文件删除对应表中的所有的数据,但是表还存在
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public void deleteByWhere(String vlaue) {
        try {
            db.delete(Person.class, WhereBuilder.b("name","=",vlaue));//根据指定的条件去删除对应表中的内容
//            db.delete(Person.class,WhereBuilder.b("name","=",vlaue).and("name","=",vlaue).and("name","=",vlaue));//多个参数一直追加
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void update(Person person) {
        try {
            db.update(person,new String[]{"",""});//更新数据,第一个参数是代后更新后数据的对象,第二个参数 是一个可变的String 字符串,代表要更新表中的哪些列
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateByWhere(Person person) {
        try {
            //根据一个查询的条件去更新对应的字段名,可能会更新多行,第一个参数是封装有更新数据的对象
            //db.update(person,WhereBuilder.b("gender","=","男"),new String[]{"name","age","gender"});
           //db.update(person,new String[]{"name","age","gender"});
            db.update(Person.class, WhereBuilder.b("name","=","xy"), new KeyValue("name","yx"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return 查询所有的数据
     */
    public List<Person>  query() {
        try {
            return db.findAll(Person.class);//返回对应表中的所有的数据
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param id 主键的值
     * @return
     */
    public Person findById(Object id) {
        try {
            return db.findById(Person.class, id);//根据指定的主键的值查询返回对应的对象
        } catch (DbException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 根据语句查询
     * @param sql  select * form person where name= xxxx
     * @return
     */
    public List<DbModel> findBySql(String sql) {
        try {
            return db.findDbModelAll(new SqlInfo(sql));//根据 sql 语句查询
            //  db.dropDb();//删除数据库文件
            // db.dropTable(Person.class);//删除某张表
            //db.addColumn(Person.class,"xuexiao");//更新某张表,增加字段,第二个参数就是增加的字段名,这个一般在 upgrade 的监听中执行
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
