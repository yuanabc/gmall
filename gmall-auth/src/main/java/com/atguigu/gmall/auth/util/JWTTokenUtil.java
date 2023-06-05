package com.atguigu.gmall.auth.util;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.auth.config.JWTConfig;
import com.atguigu.gmall.auth.security.entity.SelfUserEntity;
import com.google.common.collect.Maps;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

/**
 * JWT工具类
 *
 * @Author Sans
 * @CreateTime 2019/10/2 7:42
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JWTTokenUtil {


	/**
	 * 生成Token
	 *
	 * @Author Sans
	 * @CreateTime 2019/10/2 12:16
	 * @Param selfUserEntity 用户安全实体
	 * @Return Token
	 */
	public static String createAccessToken(SelfUserEntity selfUserEntity) {
		// 登陆成功生成JWT
		byte[] keyBytes = JWTConfig.secret.getBytes();
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);

		String token = Jwts.builder()
				// 放入用户名和用户ID
				.setId(selfUserEntity.getUserId() + "")
				// 主题
				.setSubject(selfUserEntity.getUsername())
				// 签发时间
				.setIssuedAt(new Date())
				// 签发者
				.setIssuer("abc")
				// 自定义属性 放入用户拥有权限
				.claim("authorities", JSON.toJSONString(selfUserEntity.getAuthorities()))
				// 失效时间
				.setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration * 1000))
				// 签名算法和密钥
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		return token;
	}


	/**
	 * 为指定用户生成token
	 *
	 * @param claims 用户信息
	 * @return token
	 */
	public String generateToken(Map<String, Object> claims) {
		Date createdTime = new Date();
		Date expirationTime = this.getExpirationTime();
		byte[] keyBytes = JWTConfig.secret.getBytes();
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(createdTime)
				.setExpiration(expirationTime)
				// 你也可以改用你喜欢的算法
				// 支持的算法详见：https://github.com/jwtk/jjwt#features
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * 从token中获取claim
	 *
	 * @param token token
	 * @return claim
	 */
	public Claims getClaimsFromToken(String token) {
		try {
			return Jwts.parser()
					.setSigningKey(JWTConfig.secret.getBytes())
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
			log.error("token解析错误", e);
			throw new IllegalArgumentException("Token invalided.");
		}
	}

	/**
	 * 获取token的过期时间
	 *
	 * @param token token
	 * @return 过期时间
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token).getExpiration();
	}

	/**
	 * 判断token是否过期
	 *
	 * @param token token
	 * @return 已过期返回true，未过期返回false
	 */
	private Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * 计算token的过期时间
	 *
	 * @return 过期时间
	 */
	private Date getExpirationTime() {
		return new Date(System.currentTimeMillis() + JWTConfig.expiration * 1000);
	}

	/**
	 * 判断token是否非法
	 *
	 * @param token token
	 * @return 未过期返回true，过期返回false
	 */
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

	public static void main(String[] args) {
		// 1. 初始化
		JWTTokenUtil jwtOperator = new JWTTokenUtil();

		// 2.设置用户信息
		HashMap<String, Object> objectObjectHashMap = Maps.newHashMap();
		objectObjectHashMap.put("id", "1");

		// 测试1: 生成token
//		String token = jwtOperator.generateToken(objectObjectHashMap);
////		// 会生成类似该字符串的内容: eyJhbGciOiJIUzUxMiJ9.eyJpZCI6IjEiLCJleHAiOjE1OTg3MTEzOTcsImlhdCI6MTU5ODUzODU5N30.ECwcyo-j2p_Dt4va0dtVTYGjGEF-qBIRG4Zbp3c8jFt2J7UZ1yFp_306ZhlRMqTaR2wjJbwnxlJCwKwGcTVb-A
//		System.out.println("生成token:"+token);
		SelfUserEntity selfUserEntity = new SelfUserEntity();
		selfUserEntity.setUserId(11110L);
		selfUserEntity.setUsername("张明宇");
		selfUserEntity.setEnabled(true);

		Set<GrantedAuthority> authorities = new HashSet<>();
		for(int i = 0; i < 4; i++){
			authorities.add(new SimpleGrantedAuthority("ROLE_" + i));
		}


		selfUserEntity.setAuthorities(authorities);
		String token = createAccessToken(selfUserEntity);
		System.out.println("生成token222:"+token);
//		// 将我改成上面生成的token!!!
		String someToken = token;// "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6IjEiLCJleHAiOjE1OTg3MTEzOTcsImlhdCI6MTU5ODUzODU5N30.ECwcyo-j2p_Dt4va0dtVTYGjGEF-qBIRG4Zbp3c8jFt2J7UZ1yFp_306ZhlRMqTaR2wjJbwnxlJCwKwGcTVb-A\n";
		// 测试2: 如果能token合法且未过期，返回true
		Boolean validateToken = jwtOperator.validateToken(someToken);
		System.out.println("校验token："+validateToken);
//
//		// 测试3: 获取用户信息
		Claims claims = jwtOperator.getClaimsFromToken(someToken);
		System.out.println("claims信息:"+claims);
////
		// 将我改成你生成的token的第一段（以.为边界）
		String encodedHeader = "eyJhbGciOiJIUzI1NiJ9";
		// 测试4: 解密Header
		byte[] header = org.apache.commons.codec.binary.Base64.decodeBase64(encodedHeader.getBytes());
		System.out.println("token的第一段:"+new String(header));

		// 将我改成你生成的token的第二段（以.为边界）
		String encodedPayload = "eyJpZCI6IjEiLCJpYXQiOjE1OTg1NDAxNDEsImV4cCI6MTU5ODcxMjk0MX0";
		// 测试5: 解密Payload
		byte[] payload = org.apache.commons.codec.binary.Base64.decodeBase64(encodedPayload.getBytes());
		System.out.println("token的第二段:"+new String(payload));
//
//		// 测试6: 这是一个被篡改的token，因此会报异常，说明JWT是安全的
//		jwtOperator.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJpYXQiOjE1NjU1ODk3MzIsImV4cCI6MTU2Njc5OTMzMn0.nDv25ex7XuTlmXgNzGX46LqMZItVFyNHQpmL9UQf-aUxxx");
	}
}