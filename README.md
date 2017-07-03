# Cooking Navigator
Cooing Navigator is a digital cookbook which you can easily view recipes, add recipes into your favorite folder. You can also add new recipes into it and update them later.

## Contributers
This project is from ECUST-Fachhochschule Luebeck 2018, IT class, SWE lecture, group Shi Wenbin, Gang Shao, Sijie Chen, Qiwen Gu and VanillaChocola.

## How to depoly
### Environment Required
updated version Eclipse, mysql, java runtime environment 6+

### Step by Step
1. Open mysql, creat a new schema 'cookbook', make sure you have got the right spelling.
2. Import the DB dump files into the schema created
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
Run it and you can see our work. Enjoy~
