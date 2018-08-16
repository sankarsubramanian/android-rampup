package test.java.att.nls;

import main.java.att.nls.Events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletInputStream;
import javax.servlet.ReadListener;


public class EventsTest {
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	Events events;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		events = new Events();
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public final void testGetEvents() {

		when(request.getParameter("subscriptionId")).thenReturn("1212-a3-ceaa");

		try {
			events.doGet(request, response);
			verify(response).setStatus(204);
		} catch(Exception e) { 
			System.out.println("Exception catch" + e);
		}
	}
	
	@Test
	public final void testUpdateEvents() throws Exception {
		
		String event = "{\n" + "\"notification\":\n" + 
				"  {\n" + 
				"   \"subscriptions\":\n" + 
				"   [\n" + 
				"     {\n" + 
				"       \"subscriptionId\":\"1212-a3-ceaa\",\n" + 
				"       \"callbackData\":\"dpt_495-c2\",\n" + 
				"       \"expiresIn\": \"100\",\n" + 
				"       \"notificationEvents\":[\n" + 
				"         {\n" + 
				"          \"type\":\"FIRSTNET_SESSION\",\n" + 
				"          \"sessionStatus\":\"uplifted\"\n" + 
				"          } \n" + 
				"        ]\n" + 
				"      } \n" + 
				"      ]\n" + 
				"     }\n" + 
				"    }";
		
		byte[] binaryData = event.getBytes();
				
		when(request.getInputStream()).thenReturn(createServletInputStream(binaryData));

		events.doPost(request, response);
		
		verify(response).setStatus(200);
		
		getEventsAfterUpdateEvents();
	}
	
	public final void getEventsAfterUpdateEvents()  throws IOException {

		when(request.getParameter("subscriptionId")).thenReturn("1212-a3-ceaa");
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(writer);

		try {
			events.doGet(request, response);
			verify(response).setStatus(200);
		} catch(Exception e) { 
			System.out.println("Exception catch" + e);
		}
	}
	
    public static ServletInputStream createServletInputStream(byte[] 
   		 object) throws Exception {
 
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
   		baos.write(object);

   		final InputStream bais = new ByteArrayInputStream(baos.toByteArray());
   		          
   		return new ServletInputStream() {

   			@Override
   		    public int read() throws IOException {
   				return bais.read();
   		    }

   			@Override
			public boolean isFinished() {
   				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener arg0) {
				// TODO Auto-generated method stub
			}
   		};
    }
}
