/*BEGIN_COPYRIGHT_BLOCK
 *
 * This file is a part of DrJava. Current versions of this project are available
 * at http://sourceforge.net/projects/drjava
 *
 * Copyright (C) 2001-2002 JavaPLT group at Rice University (javaplt@rice.edu)
 *
 * DrJava is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * DrJava is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * or see http://www.gnu.org/licenses/gpl.html
 *
 * In addition, as a special exception, the JavaPLT group at Rice University
 * (javaplt@rice.edu) gives permission to link the code of DrJava with
 * the classes in the gj.util package, even if they are provided in binary-only
 * form, and distribute linked combinations including the DrJava and the
 * gj.util package. You must obey the GNU General Public License in all
 * respects for all of the code used other than these classes in the gj.util
 * package: Dictionary, HashtableEntry, ValueEnumerator, Enumeration,
 * KeyEnumerator, Vector, Hashtable, Stack, VectorEnumerator.
 *
 * If you modify this file, you may extend this exception to your version of the
 * file, but you are not obligated to do so. If you do not wish to
 * do so, delete this exception statement from your version. (However, the
 * present version of DrJava depends on these classes, so you'd want to
 * remove the dependency first!)
 *
END_COPYRIGHT_BLOCK*/

package edu.rice.cs.drjava.model;

import java.util.LinkedList;
import edu.rice.cs.util.ReaderWriterLock;

/**
 * Base class for all component-specific EventNotifiers.  This class provides
 * common methods to manage listeners of a specific type.
 * @type T the type of the listener class to be managed
 */
public abstract class EventNotifier<T> {

  /**
   * All T Listeners that are listening to the model.
   * Accesses to this collection are protected by the ReaderWriterLock.
   * The collection must be synchronized, since multiple readers could
   * access it at once.
   */
  protected final LinkedList<T> _listeners = new LinkedList<T>();

  /**
   * Provides synchronization primitives for solving the readers/writers
   * problem.  In EventNotifier, adding and removing listeners are considered
   * write operations, and all notifications are considered read operations.
   *
   * Multiple reads are allowed simultaneously, but only one write can occur
   * at a time, and no reads can occur during a write.
   */
  protected final ReaderWriterLock _lock = new ReaderWriterLock();

  // -------------------- WRITER METHODS --------------------

  /**
   * Add a listener to the model.
   * @param listener a listener that reacts on events
   */
  public void addListener(T listener) {
    _lock.startWrite();
    try {
      _listeners.add(listener);
    }
    finally {
      _lock.endWrite();
    }
  }

  /**
   * Remove a listener from the model.
   * @param listener a listener that reacts on events
   */
  public void removeListener(T listener) {
    _lock.startWrite();
    try {
      _listeners.remove(listener);
    }
    finally {
      _lock.endWrite();
    }
  }

  /**
   * Removes all listeners from this notifier.
   */
  public void removeAllListeners() {
    _lock.startWrite();
    try {
      _listeners.clear();
    }
    finally {
      _lock.endWrite();
    }
  }
}
