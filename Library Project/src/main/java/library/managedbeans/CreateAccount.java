package library.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;

import library.businessobject.Reader;
import library.libraryservice.LibraryService;

@ManagedBean(name = "loadMoney")
@ViewScoped
public class CreateAccount implements Serializable{

	private static final long serialVersionUID = 7078809928413778000L;

	private LibraryService libraryService;

	private List<Reader> readers;
	private String cardId;
	private int amount;

	@ManagedProperty(value="#{ReaderBag}")
    private ReaderBag readerBag;
	
	@PostConstruct
	public void initialize() throws Exception{
		
		System.out.println("PA_DEBUG: init LoadMoney");
		InitialContext ctx = new InitialContext();
		libraryService = (LibraryService)ctx.lookup("java:global/Library-0.0.1/LibraryBean!library.libraryservice.LibraryService");
		
		readers = Reader.convertFromMapList(libraryService.getReaders());
		amount = 0;
	}
	
	public void onSelectedCardId(){
		if(cardId != null && !cardId.isEmpty()) {
			Reader reader = Reader.convertFromMap(libraryService.getReaderFromCardId(cardId));
			if(reader != null) {
				readerBag.setCurrentReader(Reader.convertToMap(reader));
			}
		}
		amount = 0;
	}
	
	public void onSelectedAmount() {
		if(amount > 0) {
			Reader reader = Reader.convertFromMap(readerBag.getCurrentReader());
			reader.setAccountBalance(reader.getAccountBalance()+amount);
			libraryService.updateReader(Reader.convertToMap(reader));
		}
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int a) {
		amount = a;
	}
	public List<Reader> getReaders() {
		return readers;
	}


	public void setReaders(List<Reader> readers) {
		this.readers = readers;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public ReaderBag getReaderBag() {
		return readerBag;
	}

	public void setReaderBag(ReaderBag readerBag) {
		this.readerBag = readerBag;
	}

}