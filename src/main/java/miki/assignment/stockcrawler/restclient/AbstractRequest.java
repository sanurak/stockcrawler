package miki.assignment.stockcrawler.restclient;

import com.google.api.client.util.Key;

public class AbstractRequest<R> implements Request<R> {
	@Key
	private String username;
	@Key
	private String password;

	@Key
	private R requestObject;

	@Override
	public final void setUsername(String username) {
		this.username = username;

	}

	@Override
	public final String getUsername() {
		return this.username;
	}

	@Override
	public final void setPassword(String password) {
		this.password = password;
	}

	@Override
	public final String getPassword() {
		return this.password;
	}

	@Override
	public final void setRequestObject(R r) {
		this.requestObject = r;
	}

	@Override
	public final R getRequestObject() {
		return this.requestObject;
	}

}
