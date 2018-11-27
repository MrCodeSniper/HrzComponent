package com.mujirenben.android.xsh.utils;

import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static okhttp3.internal.http.HttpHeaders.contentLength;
import static okhttp3.internal.http.StatusLine.HTTP_CONTINUE;

/**
 * Created by Tim on 2016/12/22.
 */

public class MyIntercepter implements Interceptor
{

	public static String TAG			= "httptest";
	public static boolean	logHeader	= true;

	private Logger			logger		= new Logger();

	@Override
	public Response intercept(Chain chain) throws IOException
	{
		Request request = chain.request();

		StringBuilder builder = new StringBuilder();
		builder.append("okhttp:").append("\n");

		RequestBody requestBody = request.body();
		Connection connection = chain.connection();
		Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
		String requestMessage = request.method() + ' ' + request.url() + ' ' + protocol;
		builder.append("okhttp: " + requestMessage).append("\n");
		Headers requ_headers = request.headers();
		for (int i = 0, count = requ_headers.size(); i < count; i++)
		{
			String name = requ_headers.name(i);
			String value = requ_headers.value(i);
			if (logHeader)
			{
				builder.append("header : " + name + "=" + value).append("\n");
			}
		}
		if (requestBody != null)
		{
			Buffer buffer = new Buffer();
			requestBody.writeTo(buffer);
			Charset charset = Charset.forName("utf-8");
			MediaType contentType = requestBody.contentType();
			if (contentType != null)
			{
				charset = contentType.charset(charset);
			}
			if (bodyEncoded(request.headers()))
			{
				builder.append("okhttp: body = encoded body omitted").append("\n");
			}
			else
			{
				if (isPlaintext(buffer))
				{
					builder.append("okhttp: request body = " + buffer.readString(charset)).append("\n");
				}
				else
				{
					builder.append("okhttp: binary length = " + requestBody.contentLength()).append("\n");
				}
			}
		}

		Response response;
		try
		{
			response = chain.proceed(request);
		}
		catch (Exception e)
		{
			builder.append("okhttp: http failed = " + e.getMessage());
			logger.log(builder.toString());
			throw e;
		}
		ResponseBody responseBody = response.body();
		long contentLength = responseBody.contentLength();
		String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
		builder.append("okhttp: " + response.code() + " " + response.message()).append("\n");

		Headers resp_header = response.headers();
		for (int i = 0, count = resp_header.size(); i < count; i++)
		{
			String name = resp_header.name(i);
			String value = resp_header.value(i);
			if (logHeader)
			{
				builder.append("header : " + name + "=" + value).append("\n");
			}
		}
		if (!hasBody(response))
		{
			builder.append("okhttp: no response body").append("\n");
		}
		else if (bodyEncoded(response.headers()))
		{
			builder.append("okhttp: encoded body omitted").append("\n");
		}
		else
		{
			BufferedSource source = responseBody.source();
			source.request(Long.MAX_VALUE);
			Buffer buffer = source.buffer();

			Charset charset = Charset.forName("utf-8");
			MediaType contentType = responseBody.contentType();
			if (contentType != null)
			{
				try
				{
					charset = contentType.charset(charset);
				}
				catch (UnsupportedCharsetException e)
				{
					builder.append("okhttp: Couldn't decode the response body; charset is likely malformed.")
							.append("\n");
					logger.log(builder.toString());
					return response;
				}
			}
			if (!isPlaintext(buffer))
			{
				builder.append("okhttp: binary body omitted").append("\n");
				logger.log(builder.toString());
				return response;
			}
			if (contentLength != 0)
			{
				String bodyStr = buffer.clone().readString(charset);
				if (bodyStr.startsWith("<!DOCTYPE html>"))
				{
					bodyStr = "The body is A html file";
				}
				builder.append("okhttp: response body = " + bodyStr).append("\n");
			}
		}
		logger.log(builder.toString());
		return response;
	}

	public static boolean hasBody(Response response)
	{
		if (response.request().method().equals("HEAD"))
		{
			return false;
		}
		int responseCode = response.code();
		if ((responseCode < HTTP_CONTINUE || responseCode >= 200) && responseCode != HTTP_NO_CONTENT
				&& responseCode != HTTP_NOT_MODIFIED)
		{
			return true;
		}
		// If the Content-Length or Transfer-Encoding headers disagree with the response code, the
		// response is malformed. For best compatibility, we honor the headers.
		if (contentLength(response) != -1 || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding")))
		{
			return true;
		}
		return false;
	}

	public boolean isPlaintext(Buffer buffer)
	{
		try
		{
			Buffer prefix = new Buffer();
			long byteCount = buffer.size() < 64 ? buffer.size() : 64;
			buffer.copyTo(prefix, 0, byteCount);
			for (int i = 0; i < 16; i++)
			{
				if (prefix.exhausted())
				{
					break;
				}
				int codePoint = prefix.readUtf8CodePoint();
				if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint))
				{
					return false;
				}
			}
			return true;
		}
		catch (EOFException e)
		{
			return false;
		}
	}

	private boolean bodyEncoded(Headers headers)
	{
		String contentEncoding = headers.get("Content-Encoding");
		return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
	}

	private class Logger
	{
		public void log(String message)
		{
			Log.d(TAG, message);
		}
	}

}
