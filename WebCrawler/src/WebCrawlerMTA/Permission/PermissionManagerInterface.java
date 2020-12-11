package WebCrawlerMTA.Permission;

import java.util.List;
/**
 * Class defining interface of PermissionManager
 * Defines the methods that need to be implemented into PermissionManager class.
 *
 * @author Chirita Gabriela
 */
public interface PermissionManagerInterface {
    public void GetPermissions(String urlString);
    public Boolean AllowedToCrawl(String url);
}
