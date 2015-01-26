package web.rufer.swisscom.sms.api.domain;

public class SendSMSRequest {

    private OutboundSMSMessageRequest outboundSMSMessageRequest;

    public SendSMSRequest() {
    }

    public OutboundSMSMessageRequest getOutboundSMSMessageRequest() {
        return outboundSMSMessageRequest;
    }

    public void setOutboundSMSMessageRequest(OutboundSMSMessageRequest outboundSMSMessageRequest) {
        this.outboundSMSMessageRequest = outboundSMSMessageRequest;
    }
}
