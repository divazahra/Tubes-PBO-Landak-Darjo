class PS5 extends PS {
    private int extraController;
    private String extraVRDevice;

    public PS5(String id, int extraController, String extraVRDevice) {
        super(id);
        this.extraController = extraController;
        this.extraVRDevice = extraVRDevice;
    }

    @Override
    public double hitungHarga(int jam) {
        double price = jam * 15000;
        price += extraController * 7000;
        if (extraVRDevice.equals("iya")) {
            price += 10000;
        }
        return price;
    }
}