/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp3playerproject;

/**
 *
 * @author Sato Masaya
 */
public interface ShuffleEngine {
    public void setSongs(Song[] songs);
    public Song getNextSong();
    public Song[] peekQueue();
}

