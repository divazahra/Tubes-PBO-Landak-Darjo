class PS4 extends PS {
    private int extraController;

    public PS4(String id, int extraController) {
        super(id);
        this.extraController = extraController;
    }

    @Override
    public double hitungHarga(int jam) {
        return (jam * 10000) + (extraController * 5000);
    }
}