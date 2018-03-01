/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//変更テスト3/1,19:59

package mp3playerproject;
import java.util.*;

import java.io.File;
import java.net.MalformedURLException;
 
import javafx.scene.media.AudioClip;
/**
 * play「再生」、next「次へ再生」、shuffle「シャッフル再生」の仕様
 * @author Sato Masaya
 */
public class Engine implements ShuffleEngine{
        
        /**先読みする曲の上限数*/
        public final Integer PEEKMAX = 5;
        
        /**曲の総数*/
        public final Integer TOTAL = 20;
        
        /**再生番号*/
        public int NowPlayingNumber = 0;
        //playSong()を使用した時、再生番号NowPlayingNumberに対応するリストの曲を再生
        
        /**PEEKMAX分の曲名を保存するテキスト*/
        String SongOrderText = "";
        //このテキストは、テキストフィールドSongOrderTextFieldに表示される
        
        /**今のリスト*/
        List<Song> NowList = new ArrayList<Song>();
        
        /**過去のリスト*/
        List<Song> OldList = new ArrayList<Song>();
        /*今のリストと過去のリストはシャッフルメソッドで使う。
        シャッフル前のリストを過去のリストへ
        シャッフル後のリストを今のリストへ保存するために
        用意した。
        */
    
        /**
         * 曲の再生メソッド
         * 
         * 今のリストを参照し、
         * 再生番号に対応するリストの曲を再生するメソッド
         */
        public void playSong(){
            AudioClip c = new AudioClip(new File(NowList.get(NowPlayingNumber).name).toURI().toString());
            c.play();
        }
        
        /**
         * 今のリストから先読みするメソッド
         * @return Songs
         * PEEKMAXの数を上限として,
         * 次に再生する予定の曲を先読みして配列として返します。
         * ただし、次に返す曲の状態（再生番号）は変わりません。
         */
        public Song[] peekQueue(){
            
            //PEEKMAXの数を上限とする配列を宣言
            Song[] Songs = new Song[PEEKMAX];
            
            /*
            配列に今のリストNowListの曲を入れる。
            今の曲番号（NowPlayingNumber）から数えてPEEKMAX分の曲を入れる。
            順に入れていって最後の曲を入れても、未代入のSongs[i]があるとき
            最初の曲を順に入れる。
            */
            for (int i = 0; i < PEEKMAX; i++) {
               Songs[i] = NowList.get((NowPlayingNumber + i) % TOTAL);
            }
           
            return Songs;
        }
        
        /**
         * 今のリストNowListにすべての曲を保存するメソッド
         * @param songs 曲の配列 
        */
        public void setSongs(Song[] songs){
            
            //今のリストNowListにすべての曲（TOTAL分の曲）を保存
            for (int i = 0; i < TOTAL; i++) {
                NowList.add(songs[i]);
            }
            
        }
 
        /**
         * 次に再生する曲を手に入れるメッソド
         * @return nextSong
         */
        public Song getNextSong(){
            //Song型で宣言
            Song nextSong = NowList.get(NowPlayingNumber);
            
            //再生番号を+１する
            NowPlayingNumber++;
            
            //もしすべての曲が流れたら、最初の曲に戻る
            if (NowPlayingNumber == TOTAL) {
                NowPlayingNumber = 0;
            }
           
            return nextSong;
           
        }
        
        /**
         *  シャッフルメソッド
         *　最初に1巡分の曲順を決定しておくロジック
         *  曲の再生が2巡目になったら,1巡目と違う順番になるよう。
         */
        public void Shuffle(){
            
            //今のリストのすべての曲を過去のリストへコピー
            for (int i = 0; i < TOTAL; i++) {
               OldList.add(i, NowList.get(i));
            }
            
            //今のリストの曲の順番をシャッフル
            Collections.shuffle(NowList);
            
            /*
            ここでは、過去のリスト（シャッフル前の曲の順番）と
            今のリスト（シャッフル後の曲の順番）を比較している。
            */
            for (int i = 0; i < TOTAL; i++) {
                
                //２つのリストが違うなら、ループから出る（条件を満たすシャッフルが成功）
                if(OldList.get(i)!=NowList.get(i)){
                    break;
                }
                
                //２つのリストが同じなら、今のリストをシャッフルし、ループをもう一度やり直す。(シャッフル失敗）
                else if(i==(TOTAL-1)){
                    Collections.shuffle(NowList);
                    i=0;
                }
            }

            
            //プロンプト上に曲順と曲名を表示
            for (int i = 0; i < TOTAL; i++) {
                System.out.print(NowList.get(i).name);
            }
            System.out.println();

            
            //再生番号を１番（リストの要素は０に対応）にする
            NowPlayingNumber = 0;
        }
        
        /**
         * 曲順を手に入れるメソッド
         * @return SongOrderText
         */
        public String getSongOrderText(){
            //SongOrderTextにPEEKMAX分の曲名を曲順に並べて保存している。
            for (int i = 0; i < PEEKMAX; i++) {
                SongOrderText += "#" + (i + 1) + "," + peekQueue()[i].name + " ";
            }
            
            return SongOrderText;
            
            //ここから下は気にしないでください。
            //SongOrderTextにPEEKMAX分の曲名を保存している
            //for (int i = 0; i < PEEKMAX; i++) {
            //    if (i == NowPlayingNumber) {
            //        SongOrderText += "♪" + peekQueue()[i].name + " ";
            //    }else{
            //        SongOrderText += "#" + (i + 1) + "," + peekQueue()[i].name + " ";
            //    }
        }
}
