package com.example.cognito.mercado.security.jwt;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class AwsCognitoRSAKeyProvider implements RSAKeyProvider {
    private final URL awsStoreUrl;

    private final JwkProvider provider;

    public AwsCognitoRSAKeyProvider(String awsCognitoRegion,String identityPoolUrl){
        String jwtUrl = "https://cognito-idp.us-east-1.amazonaws.com/us-east-1_ZPd3KfbEf/.well-known/jwks.json";
        String url =String.format(jwtUrl,awsCognitoRegion,identityPoolUrl);
        try{
            awsStoreUrl = new URL(url);
        }catch (MalformedURLException e){
            throw  new RuntimeException(String.format("Invalid Url provided, URL=%s",url));
        }
        provider = new JwkProviderBuilder(awsStoreUrl).build();
    }


    @Override
    public RSAPublicKey getPublicKeyById(String kid) {
       try {
           return (RSAPublicKey) provider.get(kid).getPublicKey();
       } catch (JwkException e){
           throw new RuntimeException(String.format("Failed to get JWT kid=%s from aws_kid_store_url=%s",kid,awsStoreUrl));
       }
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return null;
    }

    @Override
    public String getPrivateKeyId() {
        return null;
    }
}
