-- Example xml table setup taken from:
-- http://www.halhayes.com/blog/PermaLink,guid,e907445e-448e-4768-a364-cae81530967a.aspx
CREATE TABLE xmlData(
       ID int IDENTITY(1,1) NOT NULL,
       customerDocs xml NOT NULL,
       updated datetime NOT NULL DEFAULT (getdate()),
 CONSTRAINT [PK_xmlData] PRIMARY KEY CLUSTERED
(
       [ID] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO