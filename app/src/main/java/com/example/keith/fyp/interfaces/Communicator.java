package com.example.keith.fyp.interfaces;

/**
 * Communicator is an interface
 * that bridge the communication between two objects such
 * that the objects should not be aware of each other
*
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public interface Communicator {

    /**
     * A method that will be called when the first object
     * want to send an integer data to the second object
     *
     * @param index
     *            an integer data that is going to be send to the second object.
     */
    void respond(int index);
}
