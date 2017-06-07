package test;

import java.io.Reader;
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
    
    public void getRecipeList(String name){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	RecipeOperation recipeOperation=session.getMapper(RecipeOperation.class);           
            List<Recipe> recipes = recipeOperation.selectRecipes(name);
            for(Recipe recipe:recipes){
                System.out.println(recipe.getName()+":"+recipe.getDescription());
            }

        } finally {
            session.close();
        }
    }
    
    /**
     * 测试增加,增加后，必须提交事务，否则不会写入到数据库.
     */
//    public void addUser(){
//        User user=new User();
//        user.setUserAddress("家庭住址");
//        user.setUserName("飞鸟");
//        user.setUserAge(80);
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IUserOperation userOperation=session.getMapper(IUserOperation.class);
//            userOperation.addUser(user);
//            session.commit();
//            System.out.println("当前增加的用户 id为:"+user.getId());
//        } finally {
//            session.close();
//        }
//    }
//    
//    public void updateUser(){
//        //先得到用户,然后修改，提交。
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IUserOperation userOperation=session.getMapper(IUserOperation.class);
//            User user = userOperation.selectUserByID(5);            
//            user.setUserAddress("原来是魔都的浦东创新园区");
//            userOperation.updateUser(user);
//            session.commit();
//
//        } finally {
//            session.close();
//        }
//    }
//    
//    /**
//     * 删除数据，删除一定要 commit.
//     * @param id
//     */
//    public void deleteUser(int id){
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IUserOperation userOperation=session.getMapper(IUserOperation.class);           
//            userOperation.deleteUser(id);
//            session.commit(); 
//            System.out.println("当前增加的用户 id为:"+id);
//        } finally {
//            session.close();
//        }
//    }
}

