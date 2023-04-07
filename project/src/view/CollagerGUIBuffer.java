package view;

import java.io.IOException;
import java.nio.CharBuffer;

import javax.swing.*;

/**
 * This is the class representation for an Appendable. This class
 * is used for the GUI.
 */
public class CollagerGUIBuffer implements Appendable {

  DefaultListModel<String> outputList;

  /**
   * A constructor that is used for initializing the buffer with a list.
   * @param outputList represents the list being initialized.
   */
  public CollagerGUIBuffer(DefaultListModel<String> outputList) {
    this.outputList = outputList;
  }

  /**
   * Appends the specified character sequence to this {@code Appendable}.
   * <p> Depending on which class implements the character sequence
   * {@code csq}, the entire sequence may not be appended.  For
   * instance, if {@code csq} is a {@link CharBuffer} then
   * the subsequence to append is defined by the buffer's position and limit.
   * @param csq The character sequence to append.
   *            If {@code csq} is {@code null}, then the four characters
   *            {@code "null"} are appended to this Appendable.
   * @return A reference to this {@code Appendable}.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    String characterSequence = (String) csq;
    this.outputList.add(1, characterSequence);
    return this;
  }

  /**
   * Appends a subsequence of the specified character sequence to this
   * {@code Appendable}.
   *
   * An invocation of this method of the form {@code out.append(csq, start, end)}
   * when {@code csq} is not {@code null}, behaves in
   * exactly the same way as the invocation.
   * <pre>
   *     out.append(csq.subSequence(start, end)) </pre>
   * @param csq   The character sequence from which a subsequence will be
   *              appended.  If {@code csq} is {@code null}, then characters
   *              will be appended as if {@code csq} contained the four
   *              characters {@code "null"}.
   * @param start The index of the first character in the subsequence.
   * @param end   The index of the character following the last character in the
   *              subsequence.
   * @return A reference to this {@code Appendable}.
   * @throws IndexOutOfBoundsException If {@code start} or {@code end} are negative, {@code start}
   *                                   is greater than {@code end}, or {@code end} is greater than
   *                                   {@code csq.length()}.
   * @throws IOException               If an I/O error occurs.
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    String characterSequence = (String) csq;
    this.outputList.add(1, characterSequence);
    return this;
  }

  /**
   * Appends the specified character to this {@code Appendable}.
   * @param c The character to append
   * @return A reference to this {@code Appendable}
   * @throws IOException If an I/O error occurs
   */
  @Override
  public Appendable append(char c) throws IOException {
    String characterSequence = String.valueOf(c);
    this.outputList.add(1, characterSequence);
    return this;
  }
}
