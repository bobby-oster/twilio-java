package com.twilio.sdk.fetchers.taskrouter.v1.workspace.worker;

import com.twilio.sdk.clients.TwilioRestClient;
import com.twilio.sdk.converters.MarshalConverter;
import com.twilio.sdk.exceptions.ApiConnectionException;
import com.twilio.sdk.exceptions.ApiException;
import com.twilio.sdk.fetchers.Fetcher;
import com.twilio.sdk.http.HttpMethod;
import com.twilio.sdk.http.Request;
import com.twilio.sdk.http.Response;
import com.twilio.sdk.resources.RestException;
import com.twilio.sdk.resources.taskrouter.v1.workspace.worker.WorkersStatistics;
import org.joda.time.DateTime;

public class WorkersStatisticsFetcher extends Fetcher<WorkersStatistics> {
    private final String workspaceSid;
    private Integer minutes;
    private DateTime startDate;
    private DateTime endDate;
    private String taskQueueSid;
    private String taskQueueName;
    private String friendlyName;

    /**
     * Construct a new WorkersStatisticsFetcher.
     * 
     * @param workspaceSid The workspace_sid
     */
    public WorkersStatisticsFetcher(final String workspaceSid) {
        this.workspaceSid = workspaceSid;
    }

    /**
     * The minutes.
     * 
     * @param minutes The minutes
     * @return this
     */
    public WorkersStatisticsFetcher setMinutes(final Integer minutes) {
        this.minutes = minutes;
        return this;
    }

    /**
     * The start_date.
     * 
     * @param startDate The start_date
     * @return this
     */
    public WorkersStatisticsFetcher setStartDate(final DateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * The end_date.
     * 
     * @param endDate The end_date
     * @return this
     */
    public WorkersStatisticsFetcher setEndDate(final DateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * The task_queue_sid.
     * 
     * @param taskQueueSid The task_queue_sid
     * @return this
     */
    public WorkersStatisticsFetcher setTaskQueueSid(final String taskQueueSid) {
        this.taskQueueSid = taskQueueSid;
        return this;
    }

    /**
     * The task_queue_name.
     * 
     * @param taskQueueName The task_queue_name
     * @return this
     */
    public WorkersStatisticsFetcher setTaskQueueName(final String taskQueueName) {
        this.taskQueueName = taskQueueName;
        return this;
    }

    /**
     * The friendly_name.
     * 
     * @param friendlyName The friendly_name
     * @return this
     */
    public WorkersStatisticsFetcher setFriendlyName(final String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the fetch.
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Fetched WorkersStatistics
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public WorkersStatistics execute(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.GET,
            TwilioRestClient.Domains.TASKROUTER,
            "/v1/Workspaces/" + this.workspaceSid + "/Workers/Statistics",
            client.getAccountSid()
        );
        
        Response response = client.request(request);
        
        if (response == null) {
            throw new ApiConnectionException("WorkersStatistics fetch failed: Unable to connect to server");
        } else if (response.getStatusCode() != TwilioRestClient.HTTP_STATUS_CODE_OK) {
            RestException restException = RestException.fromJson(response.getStream(), client.getObjectMapper());
            if (restException == null) {
                throw new ApiException("Server Error, no content");
            }
        
            throw new ApiException(
                restException.getMessage(),
                restException.getCode(),
                restException.getMoreInfo(),
                restException.getStatus(),
                null
            );
        }
        
        return WorkersStatistics.fromJson(response.getStream(), client.getObjectMapper());
    }
}