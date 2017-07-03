# Cooking Navigator
Cooing Navigator is a digital cookbook which you can easily view recipes, add recipes into your favorite folder. You can also add new recipes into it and update them later.

## Contributers
This project is from ECUST-Fachhochschule Luebeck 2018, IT class, SWE lecture, group Shi Wenbin, Gang Shao, Sijie Chen, Qiwen Gu and VanillaChocola.

## How to depoly
### Environment Required
updated version Eclipse, mysql, java runtime environment 6+

### Step by Step
1. Open mysql, creat a new schema 'cookbook'
2. Import the DB dump files
3. Open Eclipse, clone this project and find src->model.CookBook.java, you may see the following code:<br/>
```Java
public class CookBook {
	
    /**
     * Program entry point.
     *
     * @param args  command line arguments; not used.
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException {
    	
      Application.launch(Template.class, args);

    }

}
```
<br/>
Run it and you can see our work. Enjoy~
