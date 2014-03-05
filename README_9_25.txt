===================
--- CIMSS 9.25  ---
===================

This is an update to CIMSS 9.

It contains bug fixes and styling updates to the previous release CIMSS 9.25

Update Instructions
-------------------

1) Un-deploy any existing stroke application from Tomcat
2) Rename stroke-0.9.25.war from this release package to 
   stroke.war and deploy into Tomcat


Defects fixed in this release
-----------------------------

None


Other changes
-------------

- TherapyApp 1.03 is incorporated in this release
- The CIMSS home page had three option in it menu. The 1st option now links to 
  CIMSS lookup/create case pages
- There are new CIMSS lookup/create case pages to allow users to easily launch a CIMSS
  case editing session. This is similar to the cimss_helper utilitiy. Cimss_helper was
  not packaged with CIMSS