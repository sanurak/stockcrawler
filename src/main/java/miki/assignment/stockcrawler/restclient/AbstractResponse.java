package miki.assignment.stockcrawler.restclient;

public class AbstractResponse<R> implements Response<R> {
	int status;
	String message;
	R responseObject;

	@Override
	public final void setStatus(int status) {
		this.status = status;

	}

	@Override
	public final int getStatus() {
		return this.status;
	}

	@Override
	public final void setMessage(String message) {
		this.message = message;
	}

	@Override
	public final String getMessage() {
		return this.message;
	}

	@Override
	public final void setReponseObject(R r) {
		this.responseObject = r;
	}

	@Override
	public final R getResponseObject() {
		return this.responseObject;
	}

}
