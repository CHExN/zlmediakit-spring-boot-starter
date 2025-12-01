package io.github.lunasaw.zlm.net.sse;

public record Event(String id, String event, String data, int retry) {

    @Override
    public String toString() {
        StringBuilder eventString = new StringBuilder();
        if (id != null && !id.isEmpty()) {
            eventString.append("id: ");
            eventString.append(id);
        }
        if (event != null && !event.isEmpty()) {
            eventString.append("\nevent: ");
            eventString.append(event);
        }
        if (data != null && !data.isEmpty()) {
            eventString.append("\ndata: ");
            eventString.append(data);
        }
        if (retry != 0) {
            eventString.append("\nretry: ");
            eventString.append(retry);
        }
        return eventString.toString();
    }

}
