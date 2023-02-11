
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        String[] movieFromCSV = line.split(",");

        String name = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        Movie nextMovie = new Movie(name, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        movies.add(nextMovie);

      }
      bufferedReader.close();
    } catch(IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortStrings(ArrayList<String> listToSort) {
    for (int i = 1; i < listToSort.size(); i++) {
      String temp = listToSort.get(i);

      int possibleIndex = i;
      while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.print("Enter a keyword search: ");
    String searchTerm = scanner.nextLine();
    searchTerm = searchTerm.toLowerCase();
    ArrayList<Movie> results = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++) {
      String movieKeyWords = movies.get(i).getKeywords();
      movieKeyWords = movieKeyWords.toLowerCase();

      if (movieKeyWords.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo keywords match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }

  }

  private void searchCast() {
    System.out.println(movies);
    System.out.print("Enter a person to search for (first or last name): ");
    String searchTerm = scanner.nextLine();
    searchTerm = searchTerm.toLowerCase();

    ArrayList<String> castMembers = new ArrayList<>();
    for (int i = 0; i < movies.size(); i++){
      String actors = movies.get(i).getCast();
      String[] actorList = actors.split("\\|");
      for (int j = 0; j < actorList.length; j++){
        castMembers.add(actorList[j]);
      }
    }

    for (int i = 0; i < castMembers.size(); i++) {
      for (int j = i + 1; j < castMembers.size() - 1; j++) {
        if (castMembers.get(j).equals(castMembers.get(i))) {
          castMembers.remove(j);
          j --;
        }
      }
    }

    for (int i = 0; i < castMembers.size(); i++) {
      String name = castMembers.get(i).toLowerCase();
      if (name.indexOf(searchTerm) == -1) {
        castMembers.remove(i);
        i--;
      }
    }

    if (castMembers.size() > 0) {
      // sort the results by title
      sortStrings(castMembers);

      // now, display them all to the user
      for (int i = 0; i < castMembers.size(); i++) {
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + castMembers.get(i));
      }


      System.out.println("Which actor would you like to see all movies for?");
      System.out.print("Enter number: ");
      int num = scanner.nextInt();
      scanner.nextLine();

      String selectedActor = castMembers.get(num - 1);

      int listNum = 1;
      sortResults(movies);
      for (int i = 0; i < movies.size(); i++) {
        if (movies.get(i).getCast().indexOf(selectedActor) != -1) {
          System.out.println("" + listNum + ". " + movies.get(i).getTitle());
          listNum ++;
        }
      }

      /// LEARN MORE ABOUT THE MOVIE

      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo cast members match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void listGenres() {
    ArrayList<String> genreList = new ArrayList<>();
    for (Movie movie : movies){
      String genre = movie.getGenres();
      String[] genreArray = genre.split("\\|");
      for (int i = 0; i < genreArray.length; i++){
        genreList.add(genreArray[i]);
      }
    }

    for (int i = 0; i < genreList.size(); i++) {
      for (int j = i + 1; j < genreList.size() - 1; j++) {
        if (genreList.get(j).equals(genreList.get(i))) {
          genreList.remove(j);
          j--;
        }
      }
    }

    sortStrings(genreList);

    // now, display them all to the user
    for (int i = 0; i < genreList.size(); i++) {
      int choiceNum = i + 1;
      System.out.println("" + choiceNum + ". " + genreList.get(i));
    }

    System.out.println("Which would you like to see all movies for?");
    int num = scanner.nextInt();
    scanner.nextLine();

    String genreChoice = genreList.get(num - 1);

    int listNum = 1;
    sortResults(movies);
    for (int i = 0; i < movies.size(); i++) {
      if (movies.get(i).getGenres().indexOf(genreChoice) != -1) {
        System.out.println("" + listNum + ". " + movies.get(i).getTitle());
        listNum ++;
      }
    }
  }

  private void listHighestRated() {
    /* TASK 6: IMPLEMENT ME */
  }

  private void listHighestRevenue() {
    /* TASK 6: IMPLEMENT ME */
  }
}