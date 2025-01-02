class PS3 extends PS {
    public PS3(String id) {
        super(id);
    }

    @Override
    public double hitungHarga(int jam) {
        return jam * 5000;
    }
}