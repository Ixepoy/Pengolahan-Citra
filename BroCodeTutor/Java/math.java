
public class math {
    public static void main(String[] args) {
        /*  Rumus buat random numbers
        Random random = new Random();
        int number;
        int bonus = 10;
        number = random.nextInt(1,10);
        System.out.print(number*bonus);
        */

        //Rumus math
        /*System.out.println(Math.PI);
        System.out.println(Math.E);

        double result;
        result = Math.pow(2,3);
        System.out.println(result);
        */

        //String Method
        String name = "Satria";
        int length = name.length();
        char letter = name.charAt(0);
        int index = name.indexOf("S"); //index pertama
        int lastindex = name.lastIndexOf("a"); //index terakhir

        //name = name.toUpperCase();
        //name = name.toLowerCase();
        //name = name.trim(); //buat motong spcae kosong dikedua sisinya
        name = name.replace("a", "o");
        
        System.out.println(name);
    }
}
