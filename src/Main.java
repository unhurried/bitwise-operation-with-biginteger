public class Main {

  public static void main(String[] args) {
    // c0 00
    Bytes bytes = new Bytes(new byte[] {(byte) 0xc0, (byte) 0x00});
    bytes.print();

    // c0 00 を3インクリメント => c0 03
    bytes = bytes.increment(3);
    bytes.print();

    // c0 03 & ff fe => c0 02
    bytes = bytes.and(new Bytes(new byte[] {(byte) 0xff, (byte) 0xfe}));
    bytes.print();

    // c0 02 << 1 => 80 04
    bytes = bytes.shiftLeft(1);
    bytes.print();
  }
}