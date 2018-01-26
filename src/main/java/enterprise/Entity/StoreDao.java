package enterprise.Entity;

import com.nyp.microdelivery.posting.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreDao {
    private static SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
    //company items
    public static List<Item> getAllItem(int id){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        String query="select * from storeItem where storeId="+id;

        List<Item> list = session.createNativeQuery(query).addEntity(Item.class).list();


        session.close();
        return list;

    }

    public void update(Item i){

        String query="update  enterpriseItem set item_name='"+i.getName()+"',";
        query+="description='"+i.getDescription()+"',";
        query+="price="+i.getPrice()+",";
        query+="item_type='"+i.getType()+"',";
        query+="pic='"+i.getPicture()+"'";
        query+=" where id="+i.getId();
        javax.naming.InitialContext ctx = null;
        ArrayList<Item> list=new ArrayList<>();
        try {
            ctx = new javax.naming.InitialContext();
            javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("jdbc/microDelivery");
            java.sql.Connection conn = ds.getConnection();
            Statement s=conn.createStatement();
            s.execute(query);

            s.close();
            conn.close();

        } catch (NamingException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteItem(Item i){


        Session session=sessionFactory.openSession();
        Query query=session.createQuery("delete Item where id=:itemid");
        query.setParameter("itemid",i.getId());
        session.beginTransaction();
        query.executeUpdate();

        session.close();

    }
    public void save(Item i){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.save(i);
        session.getTransaction().commit();


        session.close();
    }

    //  company
    public static List<Store> getAllComany(){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        List<Store> list = session.createNativeQuery("SELECT * FROM store").addEntity(Store.class).list();
        session.close();
        return list;

    }


}