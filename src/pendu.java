import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public  class pendu {

    public static void main(String[] args) throws Exception{

        Jeu play = new Jeu();

    }
}

// 1 création d'une class d'initialisation du jeu
class Initialisation {

    public Initialisation(int longueurList, int nbRandom, String mot, int longueurMot, List<String> listeLettres, int chance, int essais,List<String> lettresUtilise, List<String> emptyWord, String mUserName, String fileName) {
        this.mLongueurList = longueurList;
        this.mNbRandom = nbRandom;
        this.mMot = mot;
        this.mLongueurMot = longueurMot;
        this.mlistLettres = listeLettres;
        this.mChance = chance;
        this.mEssaies = essais;
        this.mLettresUtilise = lettresUtilise;
        this.mEmptyWord = emptyWord;
        this.mUserName = mUserName;
        this.mFileName = fileName;

//        System.out.println("Longueur de la liste de mot : "+mLongueurList);
//        System.out.println("Nombre random choisit dans cette liste : "+mNbRandom);
//        System.out.println("Mot correspondant au nombre choisit dans la liste : "+mMot);
//        System.out.println("Longueur du mot : "+mLongueurMot+ " lettres");
//        System.out.println("Le mot listé lettre par lettre : "+ mlistLettres);
//        System.out.println("Nombre chance : "+ mChance);
//        System.out.println("Nombre d'éssaies : "+ mEssaies);
//        System.out.println("État du mot : "+ mEmptyWord);
//        System.out.println("Vos êtes loger sous le pseudo: "+mUserName);

    }
    // variables de bases avec de valeurs par défauts
    int mLongueurList = 0;
    int mNbRandom = 0;
    String mMot = "";
    int mLongueurMot = 0;
    List<String> mlistLettres;
    int mChance = 8;
    int mEssaies = 0;
    List<String> mLettresUtilise;
    List<String> mEmptyWord;
    String mFileName;
    String mUserName;

    //2 On enregistre les mots dans une liste pour s'en servir plus tard
    public static ArrayList<String> listes(){
        ArrayList<String> maListeDeMot = new ArrayList<>();
        FileReader fr;
        try {
            fr = new FileReader(".\\src\\fichierMots\\ods6.txt");
            BufferedReader br=new BufferedReader(fr);
            String ligne;
            int cptMOT=0;
            while((ligne=br.readLine()) != null){
                maListeDeMot.add(ligne);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return maListeDeMot;
    }

    //3 on choisit un mot au hasard dans la liste
    public static int pioche(int mLongueur){
        Random random = new Random();
        return random.nextInt(0, mLongueur-1);
    }

    //4 on fait une liste dans laquelle on stock le mot lettre par lettre
    public static List<String> motListe(String mMot){
        List<String> list= new ArrayList<>();
        for(int i = 0; i < mMot.length(); i++) {
            list.add(String.valueOf(mMot.charAt(i)));
        }
        return list;
    }

    //5  création du joueur
    public static String createUser(){
        Scanner player = new Scanner(System.in);
        System.out.println("Veuillez choisir un pseudo:");
        String username = player.nextLine();
        //empêche de ne rien mettre comme pseudo
        if (username.isEmpty()){
            username = "Anonyme";
        }
        return username;
    }

}

//6 création d'une class jeu
class Jeu{

    public Jeu(){
        //initialisation des variables de bases
        int longueurList = Initialisation.listes().size();
        int nbRandom = Initialisation.pioche(longueurList);
        String mot = Initialisation.listes().get(nbRandom).toLowerCase();
        int longueurMot = mot.length();
        List<String> listLettres = Initialisation.motListe(mot);
        int chance = 8;
        int essaies = 0;
        String filename = "scores.txt";

        List<String> lettresUtilise = new ArrayList<>();

        List<String> emptyWord = new ArrayList<>();
        for (int i = 0; i < longueurMot; i++) {
            emptyWord.add("_");
        }

        // création de l'user
        String username = Initialisation.createUser();

        //lancement de l'initialisation du jeu à partir des variables créés au-dessus
        Initialisation start = new Initialisation(longueurList, nbRandom, mot, longueurMot, listLettres, chance, essaies, lettresUtilise, emptyWord, username, filename);

        launch(chance, essaies, listLettres, emptyWord, mot, lettresUtilise, username, filename, longueurMot);
    }

    // lancement de la partie
    public static void launch(int chance, int essaies, List<String> lisLettres, List<String> emptyWord, String mot,List<String> lettresUtilise, String username, String filename, int longueurMot){

        while (chance > 0){
            String lettre = request(chance, emptyWord, lettresUtilise, essaies);

            if (verif(lettre, lisLettres, emptyWord, lettresUtilise)){
                essaies++;
                chance = analyse(chance, lisLettres, lettre, emptyWord);
                if (lisLettres.equals(emptyWord)){
                    System.out.println("Bravo! @"+username+" Vous venez de trouver le mot: "+mot+"\nNombre d'éssaies : "+ essaies);
//                    loadFile(filename);
                    scoreLog(filename, essaies, longueurMot, username);
                    System.exit(0);
                }
            } 
        }
        System.out.println("Perdu!\nLe mot à deviner été : "+ mot);
    }

    //demande d'une lettre à l'utilisateur + affichage d'info sur la partie
    public static String request(int chance, List<String> emptyWord, List<String> lettresUtilise, int essaies){
        System.out.println("Liste des lettres déjà utilisé : "+ lettresUtilise);
        System.out.println("État actuel du mot"+ emptyWord);
        System.out.println("Nombre d'éssaies : "+ essaies);
        System.out.println("Vous disposez de "+ chance + " chance(s)");
        System.out.println("Choisissez une lettre :");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine().toLowerCase();
    }

    //vérification que l'utilisateur mette bien une lettre et une lettre non utilisée auparavant
    public static boolean verif(String lettre, List<String> listLettres, List<String> emptyWord, List<String> lettresUtilise){
        boolean r;
        if (!lettre.matches("[a-z]")){
            System.out.println("Vous devez obligatoirement donner une lettre!");
            r = false;
        } else if (lettresUtilise.contains(lettre)) {
            System.out.println("Cette lettre est déja utilisé! Veuillez en choisir une autre!");
            r = false;
        } else{
            r = true;
            lettresUtilise.addFirst(lettre);
        }
        return r;
    }

    public static int analyse(int chance, List<String> listLettres, String lettre, List<String> emptyWord){
        if (listLettres.contains(lettre)) {
            System.out.println("Lettre bonne!");
            resolution(lettre, listLettres, emptyWord);
        } else {
            System.out.println("Mauvaise pioche!");
            chance--;
        }
        return chance;
    }

    public static void resolution(String lettre, List<String> listLettres, List<String> emptyWord){
        for (int i = 0; i < listLettres.size(); i++) {
            if (listLettres.get(i).equals(lettre)) {
                emptyWord.set(i, lettre);
            }
        }
    }

    //création d'un fichier de log pour les joueurs remportant la partie
//    public static void loadFile(String filename){
//        File file = new File(filename);
//        try {
//            if (file.createNewFile()) {
//                System.out.println("File created: " + file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }

    //enregistrement du score du joueur dans un fichier texte
    public static void scoreLog(String filename, int essaies, int longueurMot, String username){
        int score = (longueurMot/essaies)*100;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.newLine();
            writer.write("@"+username+ ": "+score+"pts");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du score");
            e.printStackTrace();
        }
    }
}

// TODO : FUCK AI, JUST USE YOUR BRAIN AND MAKE WHAT YOU WANT!