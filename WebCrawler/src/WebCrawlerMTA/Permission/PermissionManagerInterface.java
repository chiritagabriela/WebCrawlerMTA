package WebCrawlerMTA.Permission;

import java.util.List;

public interface PermissionManagerInterface {
    public List<Permission> GetPermissions(String urlString);
    public Boolean AllowedToCrawl(String url);
}
