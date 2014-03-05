package uk.ac.leeds.lihs.auecr.cimss.stroke.access

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

class IframeDaoAuthenticationProvider extends DaoAuthenticationProvider{
	
	@Override
	public boolean supports(Class<? extends Object> authentication) {
		
	println "****  in supports *****"
	return (PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	
	protected void additionalAuthenticationChecks(UserDetails userDetails,
		UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	Object salt = null;
	
	println "In Iframe DAO provider - starting ${userDetails}<--"
	

	if (this.saltSource != null) {
		salt = this.saltSource.getSalt(userDetails);
	}

	if (authentication.getCredentials() == null) {
		logger.debug("Authentication failed: no credentials provided");

		throw new BadCredentialsException(messages.getMessage(
				"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
				includeDetailsObject ? userDetails : null);
	}

	String presentedPassword = authentication.getCredentials().toString();
	
	println "In Iframe DAO provider --> ${presentedPassword} <--"
	/* dont check password
	if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
		logger.debug("Authentication failed: password does not match stored value");

		throw new BadCredentialsException(messages.getMessage(
				"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
				includeDetailsObject ? userDetails : null);
	}
	*/
}

		
		
		
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			
			println "In here"
			Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
				messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports",
					"Only UsernamePasswordAuthenticationToken is supported"));
	
			// Determine username
			String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
			
			println "and the user is ${username}"
	
			boolean cacheWasUsed = true;
			UserDetails user = this.userCache.getUserFromCache(username);
	
			if (user == null) {
				cacheWasUsed = false;
	
				try {
					user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
				} catch (UsernameNotFoundException notFound) {
					logger.debug("User '" + username + "' not found");
	
					if (hideUserNotFoundExceptions) {
						throw new BadCredentialsException(messages.getMessage(
								"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
					} else {
						throw notFound;
					}
				}
	
				Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
			}
	
			try {
				preAuthenticationChecks.check(user);
				additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
			} catch (AuthenticationException exception) {
				if (cacheWasUsed) {
					// There was a problem, so try again after checking
					// we're using latest data (i.e. not from the cache)
					cacheWasUsed = false;
					user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
					preAuthenticationChecks.check(user);
					additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
				} else {
					throw exception;
				}
			}
	
			postAuthenticationChecks.check(user);
	
			if (!cacheWasUsed) {
				this.userCache.putUserInCache(user);
			}
	
			Object principalToReturn = user;
	
			if (forcePrincipalAsString) {
				principalToReturn = user.getUsername();
			}
			
			return createSuccessAuthentication(principalToReturn, authentication, user);
		}
	
	   

}
