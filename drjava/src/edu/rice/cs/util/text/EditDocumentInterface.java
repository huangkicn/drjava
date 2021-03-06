/*BEGIN_COPYRIGHT_BLOCK
 *
 * Copyright (c) 2001-2016, JavaPLT group at Rice University (drjava@rice.edu)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    * Neither the names of DrJava, the JavaPLT group, Rice University, nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * This software is Open Source Initiative approved Open Source Software.
 * Open Source Initative Approved is a trademark of the Open Source Initiative.
 * 
 * This file is part of DrJava.  Download the current version of this project
 * from http://www.drjava.org/ or http://sourceforge.net/projects/drjava/
 * 
 * END_COPYRIGHT_BLOCK*/

package edu.rice.cs.util.text;

import java.awt.print.Pageable;
import java.awt.print.PrinterException;
//import java.io.Serializable;

/** A GUI toolkit agnostic interface for an editable document.  The anticipated implementations are wrappers around 
  * documents generated by Swing, SWT (Eclipse), or other toolkits.  This interface also provides a mechanism for
  * restricting edits based on a conditional object, unless a separate method is called to force the edit.  This
  * interface cannot be safely implemented using the primitives in the Swing Document interface.  Write locking is
  * required to safely implement the append method.
  *
  * @version $Id: EditDocumentInterface.java 5544 2012-04-27 15:22:12Z rcartwright $
  */
public interface EditDocumentInterface {
  
  /** Style key for System.out */
  public static final String SYSTEM_OUT_STYLE = "System.out";
  
  /** Style key for System.err */
  public static final String SYSTEM_ERR_STYLE = "System.err";
  
  /** Style key for System.in */
  public static final String SYSTEM_IN_STYLE = "System.in";
  
  /** Gets the object which can determine whether an insert or remove edit should be applied, based on the inputs.
    * @return an Object to determine legality of inputs
    */
  public DocumentEditCondition getEditCondition();
  
  /** Provides an object which can determine whether an insert or remove edit should be applied, based on the inputs.
    * @param condition Object to determine legality of inputs
    */
  public void setEditCondition(DocumentEditCondition condition);
  
  /** Inserts a string into the document at the given offset and the given named style, if the edit condition allows it.
    * @param offs Offset into the document
    * @param str String to be inserted
    * @param style Name of the style to use.  Must have been added using addStyle.
    * @throws EditDocumentException if the offset is illegal
    */
  public void insertText(int offs, String str, String style);
  
  /** Inserts a string into the document at the given offset and style, regardless of the edit condition.
    * @param offs Offset into the document
    * @param str String to be inserted
    * @param style Name of the style to use.  Must have been
    * added using addStyle.
    * @throws EditDocumentException if the offset is illegal
    */
  public void forceInsertText(int offs, String str, String style);
  
  /** Removes a portion of the document, if the edit condition allows it.
    * @param offs Offset to start deleting from
    * @param len Number of characters to remove
    * @throws EditDocumentException if the offset or length are illegal
    */
  public void removeText(int offs, int len);
  
  /** Removes a portion of the document, regardless of the edit condition.
    * @param offs Offset to start deleting from
    * @param len Number of characters to remove
    * @throws EditDocumentException if the offset or length are illegal
    */
  public void forceRemoveText(int offs, int len);
  
  /** @return the length of the document. */
  public int getLength();
  
  /** Returns a portion of the document. Differs from getText in 
   * AbstractDocumentInterface by throwing EditDocumentException instead of 
   * BadLocationException.  (Why bother? It avoids referencing a Swing class.)
   * @param offs First offset of the desired text
   * @param len Number of characters to return
   * @return a portion of the document
   * @throws EditDocumentException if the offset or length are illegal
   */
  public String getDocText(int offs, int len);
  
  /** Appends a string to this in the given named style, if the edit condition allows it.
    * @param str String to be inserted
    * @param style Name of the style to use.  Must have been added using addStyle.
    * @throws EditDocumentException if the offset is illegal
    */
  public void append(String str, String style);
  
  /** @return the String identifying the default style for this document if one exists; null otherwise. */
  public String getDefaultStyle();
  
  /** Returns the Pageable object for printing.
    * @return A Pageable representing this document.
    */
  public Pageable getPageable() throws IllegalStateException;
  
  /** Prints the given console document 
   * @throws PrinterException if printing fails
   */
  public void print() throws PrinterException;
}
