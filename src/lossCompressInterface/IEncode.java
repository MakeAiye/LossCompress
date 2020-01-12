package lossCompressInterface;

import java.util.LinkedHashMap;

/*
 * 编码接口
 */
public interface IEncode {
	public void Encoding();
	public static final char FLAG = '$';
	public abstract LinkedHashMap GetMap();
	public abstract String GetOutFilePath();
}
