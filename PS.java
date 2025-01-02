abstract class PS {
    protected String id;
    protected boolean available;

    public PS(String id) {
        this.id = id;
        this.available = true;
    }

    // public String getId() {
    //     return id;
    // }

    // public void setId(String id) {
    //     this.id = id;
    // }

    // public String getType() {
    //     return getClass().getSimpleName();
    // }

    // public int getExtraController() {
    //     return 0;
    // }

    // public String getExtraVRDevice() {
    //     return "tidak";
    // }

    // public boolean getStatus() {
    //     return available;
    // }

    // public void setStatus(boolean available) {
    //     this.available = available;
    // }

    public abstract double hitungHarga(int jam);
}