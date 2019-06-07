package library.managedbeans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import library.businessobject.Librarian;
import library.businessobject.Library;
import library.businessobject.Reader;
import library.libraryservice.BagService;
import library.libraryservice.LibraryService;
import library.businessobject.Book;

public class PresentationBean {

	//	private static final long serialVersionUID = 1L;

	private LibraryService libraryService;
	private BagService bagService;

	@PostConstruct
	public void initialize() throws Exception{

		// Create reference to book EJB using JNDI
		InitialContext ctx = new InitialContext();
		libraryService = (LibraryService)ctx
				.lookup("java:global/Library-0.0.1/LibraryBean!library.libraryservice.LibraryService");

		bagService =  (BagService)ctx
				.lookup("java:global/Library-0.0.1/BagBean!library.libraryservice.BagService");

		//init library if needed
		libraryService.populateLibraryDB();

	}

	//	public BagService getBagService() {
	//		return bagService;
	//	}
	//	
	public List<Library> getLibraries() {
		return libraryService.getLibraries();
	}


	public Book getBook(String bookId) {
		return libraryService.getBook(bookId);
	}

	public List<Book> getBooksByAuthor(String author) {
		return libraryService.getBooksByAuthor(author);
	}

	public List<Book> getBooksByLanguage(String lang) {
		return libraryService.getBooksByLanguage(lang);
	}

	public List<Book> getBookList() {
		return libraryService.getBooks();
	}


	public List<Librarian> getLibrarians() {
		return libraryService.getLibrarians();
	}


	public List<Reader> getReaders() {
		return libraryService.getReaders();
	}

	public Reader getReader(long Id) {
		return libraryService.getReader(Id);
	}

	public void addBookToBag(Book b) {
		bagService.addBook(b);
		System.out.println(bagService.toString());
	}

	public void removeBookFromBag(Book b) {
		bagService.removeBook(b);
	}

	public Reader getCurrentReader() {
		return bagService.getCurrentReader();
	}

	public void setCurrentReader(Reader r) {
		bagService.setCurrentReader(r);
	}

	public String getCurrentCardId() {
		return bagService.getCardId();
	}
	
	public boolean isBookInBag(String bookId) {
		return bagService.isBookInBag(bookId);
	}

	public String chooseAmount() {

		if(!bagService.getCardId().equals("")) {
			System.out.println("PA_DEBUG: "+bagService.getCardId());
			Reader reader = libraryService.getReaderFromCardId(bagService.getCardId());
			bagService.setCurrentReader(reader);
			return "loadAmount?faces-redirect=true";
		}

		return "loadMoney?faces-redirect=true";
	}

}