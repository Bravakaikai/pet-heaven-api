package com.example.petheaven.utils

import org.apache.tomcat.util.codec.binary.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object SecretUtil {
    /***
     * key和iv值可以随机生成
     */
    private const val KEY = "1234567890123456"
    private const val IV = "1234567890123456"

    /***
     * 加密
     * @param  data 要加密的資料
     * @return encrypt
     */
    fun encrypt(data: String): String? {
        return encrypt(data, KEY, IV)
    }

    /***
     * param data 需要解密的資料
     * 调用desEncrypt（）方法
     */
    fun desEncrypt(data: String): String? {
        return desEncrypt(data, KEY, IV)
    }

    /**
     * 加密方法
     * @param data  要加密的資料
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     */
    private fun encrypt(data: String, key: String, iv: String): String? {
        return try {
            //"算法/模式/補碼方式"NoPadding PkcsPadding
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val blockSize = cipher.blockSize
            val dataBytes = data.toByteArray()
            var plaintextLength = dataBytes.size
            if (plaintextLength % blockSize != 0) {
                plaintextLength += (blockSize - plaintextLength % blockSize)
            }
            val plaintext = ByteArray(plaintextLength)
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.size)
            val keySpec = SecretKeySpec(key.toByteArray(), "AES")
            val ivSpec = IvParameterSpec(iv.toByteArray())
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted = cipher.doFinal(plaintext)
            Base64().encodeToString(encrypted)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 解密方法
     * @param data 要解密的資料
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     */
    private fun desEncrypt(data: String, key: String, iv: String): String? {
        return try {
            val encrypted = Base64().decode(data)
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val keySpec = SecretKeySpec(key.toByteArray(), "AES")
            val ivSpec = IvParameterSpec(iv.toByteArray())
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val original = cipher.doFinal(encrypted)
            String(original).trim { it <= ' ' }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}