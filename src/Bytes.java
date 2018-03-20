import java.math.BigInteger;
import java.nio.ByteBuffer;

public class Bytes {

  private final int byteSize;
  private final BigInteger mask;
  private final BigInteger value;

  Bytes(byte[] byteArray) {
    this(byteArray, byteArray.length);
  }

  private Bytes(byte[] byteArray, int byteSize) {
    this.byteSize = byteSize;
    ByteBuffer bb = ByteBuffer.allocate(byteSize);
    for(int i=0; i<byteSize; i++) {
      bb.put((byte) -1);
    }
    mask = new BigInteger(1, bb.array());
    value = new BigInteger(byteArray).and(mask);
  }

  public Bytes increment(Integer bitNumber) {
    BigInteger newValue = value.add(new BigInteger(bitNumber.toString())).and(mask);
    return new Bytes(newValue.toByteArray(), byteSize);
  }

  public Bytes decrement(Integer bitNumber) {
    BigInteger newValue = value.subtract(new BigInteger(bitNumber.toString())).and(mask);
    return new Bytes(newValue.toByteArray(), byteSize);
  }

  public Bytes shiftRight(Integer bitNumber) {
    BigInteger newValue = value.shiftRight(bitNumber).and(mask);
    return new Bytes(newValue.toByteArray(), byteSize);
  }

  public Bytes shiftLeft(Integer bitNumber) {
    BigInteger newValue = value.shiftLeft(bitNumber).and(mask);
    return new Bytes(newValue.toByteArray(), byteSize);
  }

  public Bytes and(Bytes operand) {
    BigInteger newValue = value.and(operand.value).and(mask);
    return new Bytes(newValue.toByteArray(), byteSize);
  }

  public Bytes or(Bytes operand) {
    BigInteger newValue = value.or(operand.value).and(mask);
    return new Bytes(newValue.toByteArray(), byteSize);
  }

  public Bytes xor(Bytes operand) {
    BigInteger newValue = value.xor(operand.value).and(mask);
    return new Bytes(newValue.toByteArray(), byteSize);
  }

  public Bytes not() {
    BigInteger newValue = value.not().and(mask);
    return new Bytes(newValue.toByteArray(), byteSize);
  }

  /** BigIntegerを固定長のbyte配列に変換する。 */
  public byte[] toByteArray() {
    byte[] ba = value.toByteArray();
    ByteBuffer bb = ByteBuffer.allocate(byteSize);

    // 余分な上位バイトを取り除く。
    if (ba.length >= byteSize) {
      bb.put(ba, ba.length-byteSize, byteSize);

    // 不足するbyte数分ByteBufferを先頭から埋める。
    } else {
      int byteSizeToFill = byteSize - ba.length;
      for(int i=0; i<byteSizeToFill; i++) {
        bb.put((byte) 0);
      }
      bb.put(ba);
    }

    return bb.array();
  }

  /** byte配列を16進数表記で1バイトずつ表示する。 */
  public void print() {
    StringBuilder sb = new StringBuilder();
    for(byte b : this.toByteArray()) {
      sb.append(String.format("%02x ", b));
    }
    System.out.println(sb.toString());
  }
}