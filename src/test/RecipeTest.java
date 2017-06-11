package test;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import inter.RecipeOperation;
import model.Recipe;

public class RecipeTest {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader; 

    static{
        try{
            reader    = Resources.getResourceAsReader("Configuration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession(){
        return sqlSessionFactory;
    }

    public static void main(String[] args) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IUserOperation userOperation=session.getMapper(IUserOperation.class);
//            User user = userOperation.selectUserByID(1);
//            System.out.println(user.getUserAddress());
//            System.out.println(user.getUserName());
//        } finally {
//            session.close();
//        }
    	RecipeTest testRecipe=new RecipeTest();
        testRecipe.getRecipeList("%");
    }
    
    public static ArrayList<Recipe> getRecipeList(String name){
        SqlSession session = sqlSessionFactory.openSession();
        
        ArrayList<Recipe> results = new ArrayList<>();
        try {
        	RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);           
            List<Recipe> recipes = recipeOperation.selectRecipes(name);
            for(Recipe recipe:recipes){
                results.add(recipe);
            }

        } finally {
            session.close();
        }
		return results;
    }
    
    /**
     * 娴嬭瘯澧炲姞,澧炲姞鍚庯紝蹇呴』鎻愪氦浜嬪姟锛屽惁鍒欎笉浼氬啓鍏ュ埌鏁版嵁搴�.
     */
//    public void addUser(){
//        User user=new User();
//        user.setUserAddress("瀹跺涵浣忓潃");
//        user.setUserName("椋為笩");
//        user.setUserAge(80);
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IUserOperation userOperation=session.getMapper(IUserOperation.class);
//            userOperation.addUser(user);
//            session.commit();
//            System.out.println("褰撳墠澧炲姞鐨勭敤鎴� id涓�:"+user.getId());
//        } finally {
//            session.close();
//        }
//    }
//    
//    public void updateUser(){
//        //鍏堝緱鍒扮敤鎴�,鐒跺悗淇敼锛屾彁浜ゃ��
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IUserOperation userOperation=session.getMapper(IUserOperation.class);
//            User user = userOperation.selectUserByID(5);            
//            user.setUserAddress("鍘熸潵鏄瓟閮界殑娴︿笢鍒涙柊鍥尯");
//            userOperation.updateUser(user);
//            session.commit();
//
//        } finally {
//            session.close();
//        }
//    }
//    
//    /**
//     * 鍒犻櫎鏁版嵁锛屽垹闄や竴瀹氳 commit.
//     * @param id
//     */
//    public void deleteUser(int id){
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IUserOperation userOperation=session.getMapper(IUserOperation.class);           
//            userOperation.deleteUser(id);
//            session.commit(); 
//            System.out.println("褰撳墠澧炲姞鐨勭敤鎴� id涓�:"+id);
//        } finally {
//            session.close();
//        }
//    }
}

