package main.java.att.nls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class representing Notification listener for AT&T push notification service.
 *
 */
@WebServlet("/events")
public class Events extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JSONArray events = new JSONArray();

    public Events() {
        super();
    }

    /**
     * {get} /events Get Notification Events per subscriber
     *
     * @param request the ServletRequest object contains the (Query string) subscriptionId Comma separated list of Subscription IDs
     * @param response the ServletResponse object contains the events <br>
     * Response: <br>
     * {Array} events array of all the notification events <br>
     * {String} subscriptionId Notification Subscription Id <br>
     * {String} callbackData Call Back Data <br>
     * {Array} notificationEvents Session events <br>
     * {String} sessionStatus Session status <br>
     * {String} type Notification Event Type <br>
     *
     * Example Response:
     * <pre>
     *  HTTP/1.1 200 OK
     *  {
     *   "events": [
     *    {
     *      "subscriptionId": "SUBSCRIPTION_ID1",
     *      "callbackData": "unique_user_id@att.com",
     *      "notificationEvents": [
     *        {
     *          "sessionStatus": "create_defined",
     *          "type": "FIRSTNET_APP_SESSION"
     *        }
     *      ]
     *     }
     *    ]
     *  }
     *  </pre>
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONArray eventsList = new JSONArray();
		PrintWriter pw = response.getWriter();
		
		String subscriptionIdList = request.getParameter("subscriptionId");
		
		System.out.println("subscriptionIdList:"+ subscriptionIdList);
	
		if ( subscriptionIdList == null ) {
			response.setStatus(400);
			return;
		}
		
		String[] subscriptionId = subscriptionIdList.split(",");

		try {
			for ( int subId = 0; subId < subscriptionId.length; subId++ ) {

				for ( int eventId = 0; eventId < events.length(); eventId++ ) {

					JSONObject eventSubscription = events.getJSONObject(eventId);
					String eventSubscriptionId = (String)eventSubscription.get("subscriptionId");

					if (subscriptionId[subId].equals(eventSubscriptionId)) {
						eventsList.put(eventSubscription);
						events.remove(eventId);
					}
				}
			}
			response.setContentType("application/json");

			if (eventsList.length() != 0) {
				JSONObject events = new JSONObject();
				events.put("events", eventsList);
				pw.print(events);
			} else {
				response.setStatus(204);
			}
		} catch(Exception e){ 
			System.out.println("Exception catch" + e);
		} 
	}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("AT&T push Notification to updateEvents");

		try { 
			String data = null; 

			ServletInputStream inputStream = request.getInputStream(); 
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 
			int bufferData, chunksize = 1024;
			byte buf[] = new byte[chunksize]; 

			while ((bufferData = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, bufferData);
			}

			data = new String(outputStream.toByteArray());

			JSONObject jsonObj = new JSONObject(data);
			
			JSONObject notification = (JSONObject)jsonObj.get("notification");

			JSONArray subscriptions = (JSONArray)notification.get("subscriptions");
			
			updateEvents(subscriptions);
			response.setStatus(200);

		}catch(Exception e){ 
			System.out.println("Exception catch" + e);
		} 
	}
	
	private void updateEvents(JSONArray subscriptions) {

		try {
			for ( int subId = 0; subId < subscriptions.length(); subId++ ) {
	
				JSONObject subscription = subscriptions.getJSONObject(subId);
		
				System.out.println("subscription:" + subscription);
				if (events.length() == 0 ) {
					events.put(subscription);
				} else {
					boolean isupdateEvents = false;

					for ( int eventId = 0; eventId < events.length(); eventId++ ) {

						String subscriptionId = (String)subscription.get("subscriptionId");
						JSONObject eventSubscription = events.getJSONObject(eventId);
						String eventSubscriptionId = (String)eventSubscription.get("subscriptionId");
						if ( subscriptionId.equals(eventSubscriptionId) ) {

							isupdateEvents = true;
							JSONArray notificationEvents = (JSONArray)eventSubscription.get("notificationEvents");
							JSONArray subscriptionEvents = (JSONArray)subscription.get("notificationEvents");

							for ( int subEventId = 0; subEventId < subscriptionEvents.length(); subEventId++ ) {
								notificationEvents.put(subscriptionEvents.getJSONObject(0));
							}
							
							eventSubscription.put("notificationEvents", notificationEvents);
						}
					}
					if (!isupdateEvents) {
						events.put(subscription);
					}
				}					
			}
		} catch(Exception e){ 
			System.out.println("Exception catch" + e);
		} 
	}
}
