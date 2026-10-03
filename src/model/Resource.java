package model;

public class Resource {

    private int resourceId;
    private String resourceName;
    private String resourceType;
    private int floor;
    private String status;

    public Resource(
            int resourceId,
            String resourceName,
            String resourceType,
            int floor,
            String status
    ) {

        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.floor = floor;
        this.status = status;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getFloor() {
        return floor;
    }

    public String getStatus() {
        return status;
    }
}