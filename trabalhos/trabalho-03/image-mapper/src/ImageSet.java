
public class ImageSet {
    private int id;
    private int blackPyxels;
    private int whitePyxels;

    public ImageSet(int id) {
        this.id = id;
        this.blackPyxels = 0;
        this.whitePyxels = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlackPyxels() {
        return blackPyxels;
    }

    public void setBlackPyxels(int blackPyxels) {
        this.blackPyxels = blackPyxels;
    }

    public void incBlackPyxels() {
        this.blackPyxels++;
    }

    public int getWhitePyxels() {
        return whitePyxels;
    }

    public void setWhitePyxels(int whitePyxels) {
        this.whitePyxels = whitePyxels;
    }

    public void incWhitePyxels() {
        this.whitePyxels++;
    }
}
